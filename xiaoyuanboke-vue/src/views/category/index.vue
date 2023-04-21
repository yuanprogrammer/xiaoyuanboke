<template>
  <div class="app-container">
    <div class="top">
      <el-button size="medium"
                 type="danger"
                 icon="el-icon-plus"
                 round
                 :disabled="!isAuth(['CATEGORY:INSERT'])"
                 @click="addFormVisible = true">添加专栏</el-button>
    </div>
    <div>
      <el-table :data="categoryList"
                size="small"
                style="width: 100%">

        <el-table-column label="编号"
                         width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <!-- 分类名称 -->
        <el-table-column label="分类"
                         width="auto">
          <template slot-scope="scope">
            <span class="name"
                  :style="scope.row.parentId !== 0 ? 'margin-left: 30px;' : ''">{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="操作"
                         width="150">
          <template slot-scope="scope">
            <el-button v-if="scope.row.parentId !== 0"
                       type="text"
                       size="small"
                       :disabled="!isAuth(['CATEGORY:UPDATE'])"
                       @click="moveCategory(scope.$index)">移动</el-button>
            <el-button type="text"
                       size="small"
                       :disabled="!isAuth(['CATEGORY:UPDATE'])"
                       @click="editName(scope.$index)">编辑</el-button>
            <el-button type="text"
                       @click="deleteCategory(scope.row.id)"
                       size="small"
                       :disabled="!isAuth(['CATEGORY:DELETE'])"
                       style="color: red">删除</el-button>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="是否显示"
                         width="120">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.isBrowse" :disabled="!isAuth(['CATEGORY:UPDATE'])">
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column align="center"
                         label="文章数"
                         width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.count }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 添加分类弹框 -->
    <el-dialog @open="dialogOpen"
               title="添加新的专栏"
               width="40%"
               :visible.sync="addFormVisible">
      <el-form :model="addForm"
               :rules="rules"
               ref="addForm"
               label-width="120px">

        <!-- 分类级别选择 -->
        <el-form-item label="分类级别选择"
                      prop="oneOrTwo">
          <el-select v-model="addForm.oneOrTwo"
                     placeholder="请选择级别">
            <el-option label="一级分类"
                       value="1"></el-option>
            <el-option label="二级分类"
                       value="2"></el-option>
          </el-select>
        </el-form-item>

        <!-- 分类名称 -->
        <el-form-item prop="name"
                      label="分类名称">
          <el-input v-model="addForm.name" maxlength="30" clearable show-word-limit></el-input>
        </el-form-item>

        <!-- 选择父级分类 -->
        <el-form-item prop="parentId"
                      v-if="addForm.oneOrTwo !== '1'"
                      label="选择父级分类">
          <el-select v-model="addForm.parentId"
                     placeholder="请选择">
            <el-option v-for="item in parentCate"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer"
           class="dialog-footer">
        <el-button type="danger"
                   @click="submitAdd('addForm')">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 编辑分类名称 -->
    <el-dialog
               title="编辑分类名称"
               width="30%"
               :visible.sync="editFormVisible">
      <el-form :model="editForm"
               label-position="top"
               label-width="120px"
               :rules="rules"
               ref="editForm">

        <!-- 分类名称 -->
        <el-form-item prop="name"
                      label="分类名称">
          <el-input v-model="editForm.name" maxlength="30" clearable show-word-limit></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer"
           class="dialog-footer">
        <el-button type="danger"
                   @click="submitEdit('editForm')">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 移动分类 -->
    <el-dialog @open="dialogOpen"
               title="移动分类"
               width="35%"
               :visible.sync="moveFormVisible">
      <el-form :model="moveForm" label-width="150px" label-position="top">

        <!-- 分类名称 -->
        <el-form-item label="分类名称">
          <el-input v-model="moveForm.name" :disabled="true"></el-input>
        </el-form-item>

        <!-- 选择父级分类 -->
        <el-form-item label="选择移动到父级分类">
          <el-select v-model="moveForm.parentId" placeholder="请选择">
            <el-option v-for="item in parentCate"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                        :disabled="item.id === moveForm.parentId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer"
           class="dialog-footer">
        <el-button type="danger"
                   @click="submitMove()">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import category from '@/api/blog/category'

export default {
  data() {
    return {
      categoryList: [],
      // 父级分类
      parentCate: [],
      // 添加专栏
      addFormVisible: false,
      addForm: {
        oneOrTwo: '1', // 一级分类还是二级分类
        name: '',
        parentId: 1, // 父级编号
      },
      // 编辑
      editFormVisible: false,
      editForm: {
        id: 0,
        name: ''
      },
      // 移动
      moveFormVisible: false,
      moveForm: {
        id: 0,
        name: '',
        parentId: 0
      },
      // 规则
      rules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' },
        ],
      }
    }
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    },
    // 编辑分类名称
    editName(index) {
      this.editForm['id'] = this.categoryList[index].id
      this.editForm['name'] = this.categoryList[index].name
      this.editFormVisible = true
    },
    // 提交编辑
    submitEdit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 提交编辑
          category.editCategory(this.editForm).then( _ => {
            this.getCategoryList()
            this.editFormVisible = false
          })
        }
      });
    },
    // 删除分类名称
    deleteCategory(id) {
      this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        category.deleteCategory(id).then( _ => {
          this.getCategoryList()
            // 提示
            this.$notify({
              title: '操作',
              message: '删除成功',
              type:'success'
            })
        })
      })
    },
    // 查询所有父级分类
    findParentCategory() {
      category.findParentCategory().then((res) => {
        this.parentCate = res.data.parentList
      })
    },
    // 添加框被打开
    dialogOpen() {
      this.findParentCategory()
    },
    // 提交添加分类
    submitAdd(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 添加
          category.addCategory(this.addForm).then(_ => {
            // 清空
            this.$refs[formName].resetFields();
            // 关闭
            this.addFormVisible = false
            // 提示
            this.$notify({
              title: '操作',
              message: '添加成功',
              type:'success'
            })
            this.getCategoryList()
          })
        }
      });
    },
    // 打开移动分类框
    moveCategory(index) {
      this.moveForm['id'] = this.categoryList[index].id
      this.moveForm['name'] = this.categoryList[index].name
      this.moveForm['parentId'] = this.categoryList[index].parentId
      this.moveFormVisible = true
    },
    // 提交移动
    submitMove() {
      category.moveCategory(this.moveForm).then(_ => {
        // 关闭
        this.moveFormVisible = false
        // 提示
        this.$notify({
          title: '操作',
          message: '移动成功',
          type:'success'
        })
        // 刷新数据
        this.getCategoryList()
      })
    },
    // 查询所有
    getCategoryList() {
      category.getCategoryList().then((res) => {
        let data = res.data.categoryList

        var list = []

        for (var i = 0; i < data.length; i++) {
          var d1 = {}
          d1['id'] = data[i].id
          d1['name'] = data[i].name
          d1['parentId'] = 0
          d1['count'] = data[i].count
          d1['isBrowse'] = true
          list.push(d1)

          var son = data[i].children
          for (var j = 0; j < son.length; j++) {
            var d1 = {}
            d1['id'] = son[j].id
            d1['name'] = son[j].name
            d1['parentId'] = data[i].id
            d1['count'] = son[j].count
            d1['isBrowse'] = true
            list.push(d1)
          }
        }
        this.categoryList = list
      })
    }
  },
  created() {
    this.getCategoryList()
  },
}
</script>

<style>
.name {
  font-weight: 600;
}
.top {
  margin-bottom: 10px;
}
</style>
