var zp3 = new zplayer({
	element: document.getElementById("player"),
	autoPlay: 0,
	/*是否开启自动播放,默认false*/
	lrcStart: 1,
	/*是否开启歌词功能 ，默认false（为true时musics集合中需要传入lrc字段。）*/
	showLrc: 0,
	/*开启歌词功能后是否展示歌词内容 ，默认false*/
	fixed: 1,
	/*是否固定在底部，默认false*/
	listFolded: 1,
	/*列表是否折叠，默认false*/
	listMaxHeight: 300,
	/*列表最大高度，默认240*/
	musics: [{
		title: "稻香",
		author: "周杰伦",
		url: "https://file.xiaoyuan-boke.com/project/music/周杰伦-稻香.mp3",
		pic: "https://file.xiaoyuan-boke.com/project/music/周杰伦.jpg",
		lrc: "[ti:稻香]\n[ar:周杰伦]\n[al:魔杰座]\n[00:-0.50]周杰伦 - 稻香\n[00:07.24]\n[00:10.08]作词：周杰伦  作曲：周杰伦\n[00:15.55]\n[00:18.26]\n[00:26.63]\n[00:30.61]对这个世界如果你有太多的抱怨\n[00:34.15]跌倒了  就不敢继续往前走\n[00:36.98]为什么  人要这么的脆弱 堕落\n[00:41.11]请你打开电视看看\n[00:42.94]多少人为生命在努力勇敢的走下去\n[00:46.87]我们是不是该知足\n[00:49.38]珍惜一切 就算没有拥有\n[00:53.01]\n[00:53.90]还记得你说家是唯一的城堡\n[00:57.50]随着稻香河流继续奔跑\n[01:00.43]微微笑 小时候的梦我知道\n[01:05.53]不要哭让萤火虫带著你逃跑\n[01:09.33]乡间的歌谣永远的依靠\n[01:12.13]回家吧 回到最初的美好\n[01:17.68]\n[01:40.86]不要这么容易就想放弃 就像我说的\n[01:44.21]追不到的梦想 换个梦不就得了\n[01:47.43]为自己的人生鲜艳上色\n[01:49.69]先把爱涂上喜欢的颜色\n[01:51.77]\n[01:52.57]笑一个吧 功成名就不是目的\n[01:55.54]让自己快乐快乐这才叫做意义\n[01:58.52]童年的纸飞机 现在终于飞回我手里\n[02:02.90]\n[02:04.16]所谓的那快乐 赤脚在田里追蜻蜓追到累了\n[02:08.16]偷摘水果被蜜蜂给叮到怕了\n[02:11.42]谁在偷笑呢\n[02:13.16]我靠着稻草人吹着风唱着歌睡着了\n[02:16.62]哦 哦 午后吉它在虫鸣中更清脆\n[02:19.53]哦 哦 阳光洒在路上就不怕心碎\n[02:22.62]珍惜一切 就算没有拥有\n[02:26.57]\n[02:27.38]还记得你说家是唯一的城堡\n[02:31.13]随着稻香河流继续奔跑\n[02:34.05]微微笑 小时候的梦我知道\n[02:39.07]不要哭让萤火虫带著你逃跑\n[02:42.81]乡间的歌谣永远的依靠\n[02:45.74]回家吧 回到最初的美好\n[02:49.90]\n[02:51.02]还记得你说家是唯一的城堡\n[02:54.49]随着稻香河流继续奔跑\n[02:57.50]微微笑 小时候的梦我知道\n[03:02.70]不要哭让萤火虫带著你逃跑\n[03:06.20]乡间的歌谣永远的依靠\n[03:09.35]回家吧 回到最初的美好"
	}, {
		title: "菊次郎的夏天",
		author: "久石譲",
		url: "https://file.xiaoyuan-boke.com/project/music/久石譲 - Summer (夏天)(PIANO VER.).mp3",
		pic: "https://file.xiaoyuan-boke.com/project/music/singer-img/菊次郎的春天.png",
	}, {
		title: "接个吻，开一枪,沈以诚,薛黛霏",
		author: "失眠飞行",
		url: "https://file.xiaoyuan-boke.com/project/music/接个吻，开一枪,沈以诚,薛黛霏 - 失眠飞行.mp3",
		pic: "https://file.xiaoyuan-boke.com/project/music/失眠飞行.jpg",
		lrc: "[00:00.000] 作词 : 沈以诚/刘思鉴/接个吻，开一枪\n[00:00.150] 作曲 : 接个吻，开一枪\n[00:00.300]编曲：接个吻，开一枪\n[00:00.800]混音：接个吻，开一枪\n[00:02.600]想要和你低空飞行\n[00:05.400]和你到处收集氧气\n[00:07.650]假如迷雾你看不清\n[00:10.440]不如坠入我的心里\n[00:13.150]想带你从吵闹到安宁\n[00:15.650]想带你从多云到转晴\n[00:17.700]想要为你整理衬衣\n[00:20.400]为你到处收集诗句\n[00:23.600]又过了每晚给你热牛奶的时间\n[00:26.400]床头还写着我们没看完的影片\n[00:28.700]离开后的时间里你是否还失眠\n[00:31.450]从不熬夜的我也明显黑了眼圈\n[00:33.700]你送的玩偶依然陪在身边\n[00:38.700]记忆逐渐浮现从开始到终点 又重演\n[00:42.700]我想和你 一起闯进森林潜入海底\n[00:47.700]我想和你 一起看日出到日落天气\n[00:52.650]我想和你穿过格林威治和时间飞行\n[00:57.700]我想见你 穿过教堂和人海拥抱你\n[01:02.499]我想和你\n[01:13.099]我想和你\n[01:23.599]想要和你低空飞行\n[01:25.900]和你到处收集氧气\n[01:28.400]假如迷雾你看不清\n[01:30.900]不如坠入我的心里\n[01:33.799]想带你从吵闹到安宁\n[01:36.549]想带你从多云到转晴\n[01:38.599]想要为你整理衬衣\n[01:41.450]为你到处收集诗句\n[01:44.450]又过了每晚给你热牛奶的时间\n[01:46.799]床头还写着我们没看完的影片\n[01:49.599]离开后的时间里你是否还失眠\n[01:52.299]从不熬夜的我也明显黑了眼圈\n[01:54.599]你送的玩偶依然陪在身边\n[01:59.599]记忆逐渐浮现从开始到终点 又重演\n[02:03.599]我想和你 一起闯进森林潜入海底\n[02:08.598]我想和你 一起看日出到日落天气\n[02:13.449]我想和你穿过格林威治和时间飞行\n[02:18.658]我想见你 穿过教堂和人海拥抱你\n[02:23.649]我想和你\n[02:33.699]我想和你\n[02:43.998]我想和你\n[02:46.098]再路过那家咖啡店\n[02:48.479]熟悉又陌生的地点\n[02:50.399]驻足希望你的身影会出现\n[02:55.998]还没说出口的抱歉\n[02:58.449]曾和你约定的诺言\n[03:00.498]抬头闭眼让泪流进心里面\n[03:04.348]我想和你 一起闯进森林潜入海底\n[03:09.449]我想和你 一起看日出到日落天气\n[03:14.479]我想和你穿过格林威治和时间飞行\n[03:18.748]我想见你 穿过教堂和人海拥抱你"
	}, {
		title: "The Truth That You Leave",
		author: "Pianoboy高至豪",
		url: "https://file.xiaoyuan-boke.com/project/music/Pianoboy高至豪 - The Truth That You Leave.mp3",
		pic: "https://file.xiaoyuan-boke.com/project/music/singer-img/Pianoboy高至豪.jpg"
	}, {
		title: "Arigato",
		author: "JulieBerga",
		url: "https://file.xiaoyuan-boke.com/project/music/Arigato.mp3",
		pic: "https://file.xiaoyuan-boke.com/project/music/Julie%20Berga.jpg",
		lrc: "[00:03.00]I'm a big girl baby, now why you acting so shady lately?\n[00:08.67]You don't need to babysit me, arigato, later dude\n[00:13.28]Gotta find another you, yeah someone new\n[00:16.36]I'm about to steal a car, on my way to a random bar\n[00:19.55]I'm a columbus, cause he went too far\n[00:22.84]I'm a big girl baby, don't protect me from the big world, baby\n[00:28.83]Oh, what you trying to protect me from?\n[00:33.94]I'm just 'tryna have some fun\n[00:37.09]But when we wake in the morning, stuck in a corner\n[00:41.17]Oh, just tell me what I have to say\n[00:45.73]I don't really like to play it safe\n[00:52.00]Arigato\n[00:58.92]Arigato\n[01:03.97]So arigato, later\n[01:07.21]I'm a bigshot baby, you can't handle my out bursts, you see\n[01:12.56]Gotta let me go crazy baby, arigato, what's the use\n[01:17.11]Everything you got to lose, is me and you\n[01:20.18]All I wanna do is run, wanna see who I become\n[01:23.68]I'm not a housewife, never will be one\n[01:27.39]I'm a bigshot baby, I'm the one who makes the big bucks baby\n[01:33.27]Oh, What you trying to protect me from?\n[01:37.52]I'm just 'tryna have some fun\n[01:41.06]But when we wake in the morning, stuck in a corner\n[01:45.36]Oh, just tell me what I have to say\n[01:49.63]I don't really like to play it safe\n[01:56.54]Arigato\n[02:02.42]Arigato\n[02:08.18]So arigato, later\n[02:11.20]What you trying to protect me from?\n[02:14.50]I'm just 'tryna have some fun\n[02:18.04]But when we wake in the morning, stuck in a corner\n[02:26.56]Arigato\n[02:32.95]So arigato, later\n[02:34.81]I'm a big girl baby, now why you acting so shady lately?\n[02:39.13]Arigato\n[02:45.29]So arigato, later"
	}]
});
