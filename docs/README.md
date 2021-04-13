# 活动类请求
## 分页获取活动信息

*作者: STEA_YY*

**请求URL**

/activity/getActivitiesPagable `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|是|页码
pageSize|int|是|分页大小

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //主键",
			"creatorUserId":"int //创建者用户id",
			"title":"string //活动标题",
			"intro":"string //活动介绍",
			"startTime":"date //活动开始时间",
			"endTime":"date //活动结束时间",
			"createTime":"date //活动创建时间",
			"exchangeAhaPoint":"double //兑换aha点数额",
			"exchangeAhaCredit":"double //兑换aha币数额",
			"codeSum":"int //活动最大允许兑换券数量"
		}]
	},
	"time":"string //响应时间"
}
```
## 获取用户个人兑换日志

*作者: STEA_YY*

**请求URL**

/activity/log/me `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|是|页码
pageSize|int|是|分页大小

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //主键",
			"userId":"int //兑换者id",
			"activityId":"int //活动id",
			"code":"string //兑换码",
			"exchangeTime":"date //兑换时间"
		}]
	},
	"time":"string //响应时间"
}
```
## 根据活动id获取活动信息

*作者: STEA_YY*

**请求URL**

/activity/{id} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|活动id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //主键",
		"creatorUserId":"int //创建者用户id",
		"title":"string //活动标题",
		"intro":"string //活动介绍",
		"startTime":"date //活动开始时间",
		"endTime":"date //活动结束时间",
		"createTime":"date //活动创建时间",
		"exchangeAhaPoint":"double //兑换aha点数额",
		"exchangeAhaCredit":"double //兑换aha币数额",
		"codeSum":"int //活动最大允许兑换券数量"
	},
	"time":"string //响应时间"
}
```
## 使用兑换码

*作者: STEA_YY*

**请求URL**

/activity/code `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
code|string|是|兑换码

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 授权鉴权类请求
## 通过账号密码登录

*作者: STEA_YY*

**请求URL**

/login/phone `POST`

**请求体**

```json
{
	"phone":"string //手机号【必须】",
	"password":"string //密码【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"token":"string //token令牌",
		"personalUserInfo":{
			"signedNotice":"boolean //是否签署服务协议",
			"signedContract":"boolean //是否签署合同",
			"authenticated":"int //是否通过身份认证",
			"ahaCredit":"double //aha币数量",
			"ahaPoint":"double //aha点数量",
			"role":{
				"id":"int //角色id",
				"name":"string //角色名称"
			},
			"oauths":[{
				"oauthType":"string //授权类型",
				"oauthId":"string //授权码"
			}],
			"userInfo":{
				"userId":"int //用户id(外键)",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"birthday":"date //用户出生日期",
				"typeId":"int //用户类别",
				"signature":"string //用户个性签名",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"academy":"string //用户学院",
				"major":"string //用户主修专业",
				"grade":"int //用户当前年级",
				"intro":"string //用户自我介绍",
				"specialtyTags":"string //用户特长标签",
				"trueName":"string //用户真实姓名",
				"compTags":"string //用户参与过比赛标签",
				"vipLevelId":"int //用户VIP等级(外键)"
			}
		}
	},
	"time":"string //响应时间"
}
```
## 通过手机号注册

*作者: STEA_YY*

**请求URL**

/register/phone `POST`

**请求体**

```json
{
	"phone":"string //手机号【必须】",
	"password":"string //密码【必须】",
	"nickname":"string //昵称【必须】",
	"signedNotice":"boolean //是否同意服务协议",
	"code":"string //短信验证码【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"token":"string //token令牌",
		"personalUserInfo":{
			"signedNotice":"boolean //是否签署服务协议",
			"signedContract":"boolean //是否签署合同",
			"authenticated":"int //是否通过身份认证",
			"ahaCredit":"double //aha币数量",
			"ahaPoint":"double //aha点数量",
			"role":{
				"id":"int //角色id",
				"name":"string //角色名称"
			},
			"oauths":[{
				"oauthType":"string //授权类型",
				"oauthId":"string //授权码"
			}],
			"userInfo":{
				"userId":"int //用户id(外键)",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"birthday":"date //用户出生日期",
				"typeId":"int //用户类别",
				"signature":"string //用户个性签名",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"academy":"string //用户学院",
				"major":"string //用户主修专业",
				"grade":"int //用户当前年级",
				"intro":"string //用户自我介绍",
				"specialtyTags":"string //用户特长标签",
				"trueName":"string //用户真实姓名",
				"compTags":"string //用户参与过比赛标签",
				"vipLevelId":"int //用户VIP等级(外键)"
			}
		}
	},
	"time":"string //响应时间"
}
```
## 绑定手机号

*作者: STEA_YY*

**请求URL**

/bind/phone `POST`

**请求体**

```json
{
	"phone":"string //手机号【必须】",
	"code":"string //验证码【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改密码

*作者: STEA_YY*

**请求URL**

/changePassword/phone/{phone} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phone|string|否|手机号
**请求体**

```json
{
	"newPassword":"string //新密码【必须】",
	"code":"string //短信验证码【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 签署服务协议

*作者: STEA_YY*

**请求URL**

/sign/notice `POST`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"string //响应数据",
	"time":"string //响应时间"
}
```
## 签署合同

*作者: STEA_YY*

**请求URL**

/sign/contract `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
file|file|否|合同签名文件
id|int|否|
userId|int|否|用户id(外键)
name|string|否|联系人
signatureFilename|string|否|签名文件名
signTime|date|否|合同签名时间

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"string //响应数据",
	"time":"string //响应时间"
}
```
## 登出

*作者: STEA_YY*

**请求URL**

/logout `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 绑定微信账号

*作者: STEA_YY*

**请求URL**

/bind/wechat `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
code|string|否|小程序请求码

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 通过微信号登录或注册

*作者: STEA_YY*

**请求URL**

/login/wechat `POST`

**请求体**

```json
{
	"nickname":"string //昵称【必须】",
	"signedNotice":"boolean //是否同意服务协议",
	"code":"string //微信code【必须】",
	"avatarUrl":"string //头像URL"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"token":"string //token令牌",
		"personalUserInfo":{
			"signedNotice":"boolean //是否签署服务协议",
			"signedContract":"boolean //是否签署合同",
			"authenticated":"int //是否通过身份认证",
			"ahaCredit":"double //aha币数量",
			"ahaPoint":"double //aha点数量",
			"role":{
				"id":"int //角色id",
				"name":"string //角色名称"
			},
			"oauths":[{
				"oauthType":"string //授权类型",
				"oauthId":"string //授权码"
			}],
			"userInfo":{
				"userId":"int //用户id(外键)",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"birthday":"date //用户出生日期",
				"typeId":"int //用户类别",
				"signature":"string //用户个性签名",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"academy":"string //用户学院",
				"major":"string //用户主修专业",
				"grade":"int //用户当前年级",
				"intro":"string //用户自我介绍",
				"specialtyTags":"string //用户特长标签",
				"trueName":"string //用户真实姓名",
				"compTags":"string //用户参与过比赛标签",
				"vipLevelId":"int //用户VIP等级(外键)"
			}
		}
	},
	"time":"string //响应时间"
}
```
# 竞赛信息和竞赛标签相关请求
## 根据id获取竞赛信息

*作者: STEA_YY*

**请求URL**

/competition/{id} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|竞赛信息id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //竞赛id",
		"competitionType":{
			"id":"int",
			"name":"string"
		},
		"name":"string //赛事名称",
		"level":"int //竞赛级别",
		"intro":"string //赛事简介",
		"picUrl":"int //赛事图片保存路径"
	},
	"time":"string //响应时间"
}
```
## 获取所有竞赛信息

*作者: STEA_YY*

**请求URL**

/competition/getAllCompetition `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //竞赛id",
		"competitionType":{
			"id":"int",
			"name":"string"
		},
		"name":"string //赛事名称",
		"level":"int //竞赛级别",
		"picUrl":"int //赛事图片保存路径"
	}],
	"time":"string //响应时间"
}
```
## 根据id获取竞赛类别

*作者: STEA_YY*

**请求URL**

/competition/type/{id} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|竞赛类别id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int",
		"name":"string"
	},
	"time":"string //响应时间"
}
```
## 获取所有竞赛类别

*作者: STEA_YY*

**请求URL**

/competition/type `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int",
		"name":"string"
	}],
	"time":"string //响应时间"
}
```
# 贡献点相关请求
## 获取订单信息

*作者: STEA_YY*

**请求URL**

/contribPoint/order/{orderId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
orderId|int|否|订单号

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //订单号",
		"user":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"project":{
			"id":"int //项目id",
			"creatorUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"name":"string //项目名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
			"tags":"string //项目标签",
			"compId":"int //赛事id(外键)",
			"awardLevel":"int //项目获奖级别",
			"awardTime":"date //项目获奖时间",
			"passed":"boolean //是否通过审核"
		},
		"totalCost":"double //订单总价",
		"status":"int //订单状态",
		"createTime":"date //订单创建时间",
		"payTime":"date //订单支付时间",
		"orderResources":[{
			"resource":{
				"id":"int //项目资源id",
				"projectId":"int //项目id(外键)",
				"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
				"fileType":"int //资源文件类型",
				"typeId":"int //资源类型id(外键)",
				"filename":"string //保存在oss里的资源文件名(包括前缀)",
				"previewUrl":"string //保存在oss里的预览文件地址",
				"download":"int //资源文件下载量",
				"score":"double //资源平均分",
				"scoreCount":"int //评分人数",
				"price":"double //资源价格",
				"discount":"double //资源折扣",
				"passed":"boolean //是否通过审核"
			},
			"discount":"double //资源折扣",
			"price":"double //贡献点小计"
		}],
		"chargedAhaCredit":"double //订单实际支付aha币",
		"chargedAhaPoint":"double //订单实际支付aha点"
	},
	"time":"string //响应时间"
}
```
## 获取用户全部订单信息

*作者: STEA_YY*

**请求URL**

/contribPoint/order/me `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //订单号",
			"user":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"project":{
				"id":"int //项目id",
				"creatorUser":{
					"userId":"int //用户id",
					"nickname":"string //用户昵称",
					"gender":"boolean //用户性别",
					"avatarUrl":"string //用户头像文件保存路径",
					"school":"string //用户学校",
					"vipLevelId":"int //用户VIP等级(外键)"
				},
				"name":"string //项目名称",
				"read":"int //点击率",
				"collect":"int //收藏数量",
				"avatarUrl":"string //团队头像url",
				"tags":"string //项目标签",
				"compId":"int //赛事id(外键)",
				"awardLevel":"int //项目获奖级别",
				"awardTime":"date //项目获奖时间",
				"passed":"boolean //是否通过审核"
			},
			"totalCost":"double //订单总价",
			"status":"int //订单状态",
			"createTime":"date //订单创建时间",
			"payTime":"date //订单支付时间",
			"orderResources":[{
				"resource":{
					"id":"int //项目资源id",
					"projectId":"int //项目id(外键)",
					"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
					"fileType":"int //资源文件类型",
					"typeId":"int //资源类型id(外键)",
					"filename":"string //保存在oss里的资源文件名(包括前缀)",
					"previewUrl":"string //保存在oss里的预览文件地址",
					"download":"int //资源文件下载量",
					"score":"double //资源平均分",
					"scoreCount":"int //评分人数",
					"price":"double //资源价格",
					"discount":"double //资源折扣",
					"passed":"boolean //是否通过审核"
				},
				"discount":"double //资源折扣",
				"price":"double //贡献点小计"
			}],
			"chargedAhaCredit":"double //订单实际支付aha币",
			"chargedAhaPoint":"double //订单实际支付aha点"
		}]
	},
	"time":"string //响应时间"
}
```
## 创建订单

*作者: STEA_YY*

**请求URL**

/contribPoint/order `POST`

**请求体**

```json
{
	"projectId":"int //项目id【必须】",
	"resourceIds":"int[] //项目资源id数组【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
## 对订单进行操作

*作者: STEA_YY*

**请求URL**

/contribPoint/order/{orderId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
orderId|int|否|订单号
action|string|否|操作，取值pay、cancel

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 获取贡献点总排行榜

*作者: STEA_YY*

**请求URL**

/contribPoint/rank `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"user":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"contribPoint":"double //贡献点",
		"rank":"long //排名"
	}],
	"time":"string //响应时间"
}
```
## 获取用户个人排名

*作者: STEA_YY*

**请求URL**

/contribPoint/rank/me `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"user":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"contribPoint":"double //贡献点",
		"rank":"long //排名"
	},
	"time":"string //响应时间"
}
```
## 获取用户个人贡献点变动日志

*作者: STEA_YY*

**请求URL**

/contribPoint/log/me `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|是|页码
pageSize|int|是|分页大小
type|int|否|类型
sortBy|string|否|排序关键字(取值time、type、contribPoint、ahaPoint、ahaCredit)
orderBy|string|否|排序方式(取值desc、asc，默认desc)

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int",
			"userId":"int //用户id",
			"type":"int //类型",
			"externalId":"int //业务外键",
			"ahaCreditAmount":"double //Aha币总额",
			"ahaPointAmount":"double //Aha点总额",
			"time":"date //发生时间"
		}]
	},
	"time":"string //响应时间"
}
```
# COS相关请求
## 获取COS公开资源上传签名

*作者: STEA_YY*

**请求URL**

/cos/sign/upload/public `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
filename|string|否|待上传文件名

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## signPostUploadToCos

*作者: STEA_YY*

**请求URL**

/cos/sign/upload/test `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
# 反馈类请求
## 分页获取用户全部反馈

*作者: STEA_YY*

**请求URL**

/feedback/me `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
status|int|否|处理状态
type|int|否|类别
lowestLevel|int|否|最低级别
highestLevel|int|否|最高级别
sortBy|string|否|排序关键字，取值time、status、type、level、replyTime
orderBy|string|否|排序方式

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int",
			"user":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"time":"date //反馈时间",
			"type":"int //反馈类型",
			"content":"string //反馈内容",
			"status":"int //反馈状态",
			"reply":"string //反馈回复",
			"replyTime":"date //反馈回复时间",
			"level":"int //反馈问题级别"
		}]
	},
	"time":"string //响应时间"
}
```
## 用户提交反馈

*作者: STEA_YY*

**请求URL**

/feedback/saveFeedback `POST`

**请求体**

```json
{
	"type":"int //反馈类型",
	"content":"string //反馈内容"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 站内信相关请求
## 根据条件获取未读消息条数

*作者: STEA_YY*

**请求URL**

/message/count/notRead `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
type|string|否|消息类型(system:系统消息,private:私信)

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
## 根据条件获取消息队列

*作者: STEA_YY*

**请求URL**

/message/getAllMessageVoPagable `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
status|int|否|状态(0:未读,1:已读,2:已删除)
type|string|否|消息类型(system:系统消息,private:私信)

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //站内信id",
			"senderUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"receiverUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"type":"int //类型",
			"status":"int //阅读状态",
			"receiveDate":"date //收件时间",
			"title":"string //信件标题",
			"content":"string //信件内容"
		}]
	},
	"time":"string //响应时间"
}
```
## 根据对方用户id获取会话消息，并标记为已读

*作者: STEA_YY*

**请求URL**

/message/communication/{senderUserId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
senderUserId|int|否|对方用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //站内信id",
		"senderUser":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"receiverUser":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"type":"int //类型",
		"status":"int //阅读状态",
		"receiveDate":"date //收件时间",
		"title":"string //信件标题",
		"content":"string //信件内容"
	}],
	"time":"string //响应时间"
}
```
## 根据id获取站内信内容，并标记为已读

*作者: STEA_YY*

**请求URL**

/message/{messageId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
messageId|int|否|站内信id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除指定站内信（并非真的从数据库中删除）

*作者: STEA_YY*

**请求URL**

/message/{messageId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
messageId|int|否|站内信id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 向指定用户发送私信

*作者: STEA_YY*

**请求URL**

/message/sendPrivateMessage `POST`

**请求体**

```json
{
	"receiverUserId":"int //收件人用户id",
	"type":"int //类型",
	"textId":"int //信件内容id(外键)",
	"title":"string //信件标题",
	"content":"string //信件内容【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 拉取群发信息

*作者: STEA_YY*

**请求URL**

/message/notice/pull `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 公告类请求
## 获取投放的系统公告

*作者: STEA_YY*

**请求URL**

/notice/getAllNoticeEnabled `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int",
		"title":"string //公告标题",
		"content":"string //公告内容",
		"createTime":"date //公告开始日期",
		"puttingStartTime":"date //公告投放开始日期",
		"puttingEndTime":"date //公告投放结束日期",
		"enable":"boolean //公告是否启用"
	}],
	"time":"string //响应时间"
}
```
# 项目相关请求
## 分页获取所有项目粗略信息

*作者: STEA_YY*

**请求URL**

/project/getAllProjectPageable `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
userId|int|否|用户id
compId|int|否|竞赛id
awardLevel|int|否|获奖级别
sortBy|string|否|排序关键字
orderBy|string|否|排序方式

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //项目id",
			"creatorUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"name":"string //项目名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
			"tags":"string //项目标签",
			"compId":"int //赛事id(外键)",
			"awardLevel":"int //项目获奖级别",
			"awardTime":"date //项目获奖时间",
			"passed":"boolean //是否通过审核"
		}]
	},
	"time":"string //响应时间"
}
```
## 分页获取登录用户所有项目粗略信息

*作者: STEA_YY*

**请求URL**

/project/me `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
compId|int|否|竞赛id
awardLevel|int|否|获奖级别
sortBy|string|否|排序关键字
orderBy|string|否|排序方式

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //项目id",
			"creatorUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"name":"string //项目名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
			"tags":"string //项目标签",
			"compId":"int //赛事id(外键)",
			"awardLevel":"int //项目获奖级别",
			"awardTime":"date //项目获奖时间",
			"passed":"boolean //是否通过审核"
		}]
	},
	"time":"string //响应时间"
}
```
## 根据项目id获取项目详细信息

*作者: STEA_YY*

**请求URL**

/project/{projectId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //项目id",
		"creatorUser":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"name":"string //团队名称",
		"avatarUrl":"string //团队头像url",
		"tags":"string //项目标签",
		"intro":"string //团队介绍(富文本)",
		"read":"int //点击率",
		"collect":"int //收藏数量",
		"compId":"int //赛事id(外键)",
		"competition":{
			"id":"int",
			"typeId":"int //竞赛类别id(外键)",
			"level":"int //竞赛级别",
			"name":"string //赛事名称",
			"intro":"string //赛事简介",
			"picUrl":"int //赛事图片保存路径"
		},
		"compName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
		"awardLevel":"int //项目获奖级别",
		"awardTime":"date //项目获奖时间",
		"awardProveUrl":"string //获奖证明文件url",
		"meaning":"double //项目资源完整程度，决定贡献点",
		"passed":"boolean //是否通过审核",
		"members":[{
			"memberUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"rank":"int //团队成员顺位(决定显示顺序，1为队长)",
			"job":"string //团队成员职务",
			"editable":"boolean //成员是否可编辑项目信息"
		}],
		"resources":[{
			"id":"int //项目资源id",
			"projectId":"int //项目id(外键)",
			"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
			"fileType":"int //资源文件类型",
			"typeId":"int //资源类型id(外键)",
			"filename":"string //保存在oss里的资源文件名(包括前缀)",
			"previewUrl":"string //保存在oss里的预览文件地址",
			"download":"int //资源文件下载量",
			"score":"double //资源平均分",
			"scoreCount":"int //评分人数",
			"price":"double //资源价格",
			"discount":"double //资源折扣",
			"passed":"boolean //是否通过审核"
		}]
	},
	"time":"string //响应时间"
}
```
## 获取COS公开资源上传签名(用于上传项目头像和获奖证明材料)

*作者: STEA_YY*

**请求URL**

/project/sign/upload/public `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
filename|string|否|待上传文件名

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 新增项目

*作者: STEA_YY*

**请求URL**

/project/saveProject `POST`

**请求体**

```json
{
	"name":"string //团队名称【必须】",
	"avatarUrl":"string //团队头像url",
	"compId":"int //赛事id(外键)【必须】",
	"compName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
	"awardLevel":"int //项目获奖级别",
	"tags":"string //项目标签",
	"awardTime":"date //项目获奖时间",
	"awardProveUrl":"string //获奖证明文件url",
	"intro":"string //团队介绍(富文本)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
## 修改项目信息

*作者: STEA_YY*

**请求URL**

/project/{projectId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
{
	"name":"string //团队名称【必须】",
	"avatarUrl":"string //团队头像url",
	"compId":"int //赛事id(外键)【必须】",
	"compName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
	"awardLevel":"int //项目获奖级别",
	"tags":"string //项目标签",
	"awardTime":"date //项目获奖时间",
	"awardProveUrl":"string //获奖证明文件url",
	"intro":"string //团队介绍(富文本)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除项目

*作者: STEA_YY*

**请求URL**

/project/{projectId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 根据项目id获取所有项目成员

*作者: STEA_YY*

**请求URL**

/project/{projectId}/members `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"memberUser":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"rank":"int //团队成员顺位(决定显示顺序，1为队长)",
		"job":"string //团队成员职务",
		"editable":"boolean //成员是否可编辑项目信息"
	}],
	"time":"string //响应时间"
}
```
## 新增项目成员

*作者: STEA_YY*

**请求URL**

/project/member/{projectId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
{
	"projectId":"int",
	"memberUserId":"int //团队成员id【必须】",
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)【必须】",
	"job":"string //团队成员职务【必须】",
	"editable":"boolean //成员是否可编辑项目信息【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改项目成员信息

*作者: STEA_YY*

**请求URL**

/project/member/{projectId}/{memberUserId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
memberUserId|int|否|成员用户id
**请求体**

```json
{
	"projectId":"int",
	"memberUserId":"int //团队成员id【必须】",
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)【必须】",
	"job":"string //团队成员职务【必须】",
	"editable":"boolean //成员是否可编辑项目信息【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改多个项目成员信息

*作者: STEA_YY*

**请求URL**

/project/members/{projectId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
[{
	"projectId":"int",
	"memberUserId":"int //团队成员id【必须】",
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)【必须】",
	"job":"string //团队成员职务【必须】",
	"editable":"boolean //成员是否可编辑项目信息【必须】"
}]
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除项目成员

*作者: STEA_YY*

**请求URL**

/project/member/{projectId}/{memberUserId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
memberUserId|int|否|成员用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 根据项目id获取所有项目资源

*作者: STEA_YY*

**请求URL**

/project/{projectId}/resources `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
edit|boolean|否|是否处于编辑模式（项目编辑权限拥有者据此获取未通过审核资源）

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //项目资源id",
		"projectId":"int //项目id(外键)",
		"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
		"fileType":"int //资源文件类型",
		"typeId":"int //资源类型id(外键)",
		"filename":"string //保存在oss里的资源文件名(包括前缀)",
		"previewUrl":"string //保存在oss里的预览文件地址",
		"download":"int //资源文件下载量",
		"score":"double //资源平均分",
		"scoreCount":"int //评分人数",
		"price":"double //资源价格",
		"discount":"double //资源折扣",
		"passed":"boolean //是否通过审核"
	}],
	"time":"string //响应时间"
}
```
## 根据项目资源id获取项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/{projectResourceId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id
edit|boolean|否|是否处于编辑模式（项目编辑权限拥有者据此获取未通过审核资源）

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //项目资源id",
		"projectId":"int //项目id(外键)",
		"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
		"fileType":"int //资源文件类型",
		"typeId":"int //资源类型id(外键)",
		"filename":"string //保存在oss里的资源文件名(包括前缀)",
		"previewUrl":"string //保存在oss里的预览文件地址",
		"download":"int //资源文件下载量",
		"score":"double //资源平均分",
		"scoreCount":"int //评分人数",
		"price":"double //资源价格",
		"discount":"double //资源折扣",
		"passed":"boolean //是否通过审核"
	},
	"time":"string //响应时间"
}
```
## 获取COS私有资源上传签名(用于上传资源文件)

*作者: STEA_YY*

**请求URL**

/project/{projectId}/resources/sign/upload/private `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
filename|string|否|待上传文件名

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 通过临时令牌获取COS私有资源上传签名(用于上传资源文件)

*作者: STEA_YY*

**请求URL**

/project/{projectId}/resources/sign/upload/private/token `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
filename|string|否|待上传文件名
token|string|是|临时令牌

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 新增项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/{projectId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
{
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)【必须】",
	"fileType":"int //资源文件类型【必须】",
	"typeId":"int //资源类型id(外键)【必须】",
	"filename":"string //保存在oss里的资源文件名(包括前缀)【必须】",
	"price":"double //资源价格【必须】",
	"discount":"double //资源折扣【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
## 通过临时令牌新增项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/{projectId}/token `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
token|string|是|临时令牌
**请求体**

```json
{
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)【必须】",
	"fileType":"int //资源文件类型【必须】",
	"typeId":"int //资源类型id(外键)【必须】",
	"filename":"string //保存在oss里的资源文件名(包括前缀)【必须】",
	"price":"double //资源价格【必须】",
	"discount":"double //资源折扣【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
## 更新项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/{projectResourceId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id
**请求体**

```json
{
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
	"fileType":"int //资源文件类型",
	"typeId":"int //资源类型id(外键)",
	"price":"double //资源价格",
	"discount":"double //资源折扣"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 使用临时令牌更新项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/{projectResourceId}/token `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id
token|string|是|临时令牌
**请求体**

```json
{
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
	"fileType":"int //资源文件类型",
	"typeId":"int //资源类型id(外键)",
	"price":"double //资源价格",
	"discount":"double //资源折扣"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/{projectResourceId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 根据临时令牌删除项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/{projectResourceId}/token `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id
token|string|是|临时令牌

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 获取项目资源文件COS下载签名

*作者: STEA_YY*

**请求URL**

/project/resource/{projectResourceId}/sign/download `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"authorization":"string //上传签名",
		"expire":"long //签名过期时间戳",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 获取项目资源文件COS阅读签名

*作者: STEA_YY*

**请求URL**

/project/resource/{projectResourceId}/sign/read `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"authorization":"string //上传签名",
		"expire":"long //签名过期时间戳",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 获取用户收藏项目列表

*作者: STEA_YY*

**请求URL**

/project/collection `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"project":{
			"id":"int //项目id",
			"creatorUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"name":"string //项目名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
			"tags":"string //项目标签",
			"compId":"int //赛事id(外键)",
			"awardLevel":"int //项目获奖级别",
			"awardTime":"date //项目获奖时间",
			"passed":"boolean //是否通过审核"
		},
		"timestamp":"date //收藏时间戳"
	}],
	"time":"string //响应时间"
}
```
## 收藏项目

*作者: STEA_YY*

**请求URL**

/project/collection/{projectId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 取消收藏

*作者: STEA_YY*

**请求URL**

/project/collection/{projectId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 判断项目是否被收藏

*作者: STEA_YY*

**请求URL**

/project/collection/check/{projectId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"boolean //响应数据",
	"time":"string //响应时间"
}
```
## 分页查看资源评价信息

*作者: STEA_YY*

**请求URL**

/project/resource/score `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
projectId|int|否|项目id
resourceId|int|否|项目资源id
lowestScore|double|否|最低分
highestScore|double|否|最高分
sortBy|string|否|排序关键字,取值time、score
orderBy|string|否|排序方式

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"user":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"resourceId":"int //项目资源id",
			"score":"double //资源评分",
			"comment":"string //评论",
			"time":"date //评价时间"
		}]
	},
	"time":"string //响应时间"
}
```
## 评价项目资源

*作者: STEA_YY*

**请求URL**

/project/resource/score/{projectResourceId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id
**请求体**

```json
{
	"score":"double //资源评分【必须】",
	"comment":"string //评论"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除项目资源评价

*作者: STEA_YY*

**请求URL**

/project/resource/score/{projectResourceId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 查看全部已购资源

*作者: STEA_YY*

**请求URL**

/project/resource/purchased `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"resource":{
			"id":"int //项目资源id",
			"projectId":"int //项目id(外键)",
			"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
			"fileType":"int //资源文件类型",
			"typeId":"int //资源类型id(外键)",
			"filename":"string //保存在oss里的资源文件名(包括前缀)",
			"previewUrl":"string //保存在oss里的预览文件地址",
			"download":"int //资源文件下载量",
			"score":"double //资源平均分",
			"scoreCount":"int //评分人数",
			"price":"double //资源价格",
			"discount":"double //资源折扣",
			"passed":"boolean //是否通过审核"
		},
		"orderId":"int //订单表id",
		"purchaseTime":"date //购买时间"
	}],
	"time":"string //响应时间"
}
```
## 判断项目资源是否已经购买

*作者: STEA_YY*

**请求URL**

/project/resource/purchased/check/{resourceId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
resourceId|int|否|项目资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"boolean //响应数据",
	"time":"string //响应时间"
}
```
## 根据项目id获取用户已购资源的id数组

*作者: STEA_YY*

**请求URL**

/project/purchased/{projectId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int[] //响应数据",
	"time":"string //响应时间"
}
```
## 获取全部竞赛资源类型取值和定价系数

*作者: STEA_YY*

**请求URL**

/project/type `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //主键",
		"name":"string //资源类型名称",
		"priceCoefficient":"double //资源价格系数"
	}],
	"time":"string //响应时间"
}
```
## 获取全部竞赛资源-获奖等级定价方案

*作者: STEA_YY*

**请求URL**

/project/financialScheme `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //主键",
		"awardLevel":"int //获奖等级",
		"priceUpperLimit":"double //资源定价上限",
		"priceLowerLimit":"double //资源定价下限"
	}],
	"time":"string //响应时间"
}
```
# 实名认证类请求
## 获取实名认证文件COS上传签名

*作者: STEA_YY*

**请求URL**

/authentication/sign/upload `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
filename|string|否|文件名

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 更新实名认证信息

*作者: STEA_YY*

**请求URL**

/authentication/updatePersonalAuthentication `PUT`

**请求体**

```json
{
	"trueName":"string //真实姓名",
	"type":"int //认证类型(学生or社会人士)",
	"studentCardFilename":"string //学生证图片文件名",
	"idCardFrontFilename":"string //身份证正面文件名",
	"idCardBackFilename":"string //身份证背面文件名"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 用户简历相关请求
## 根据用户id查看用户简历

*作者: STEA_YY*

**请求URL**

/resume/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":{},
		"userId":"int //用户手机号(业务外键)",
		"name":"string //真实姓名",
		"phone":"string //手机号",
		"email":"string //电子邮箱",
		"gender":"string //性别",
		"birth":"string //出生年月",
		"highestDegree":"string //最高学历",
		"identity":"string //当前身份",
		"currentGrade":"string //当前年级",
		"workPlace":"string //期望工作地点",
		"profession":"string //期望职业",
		"eduExperiences":[{
			"degree":"string //教育层次",
			"school":"string //学校名称",
			"specialty":"string //专业",
			"grade":"string //专业排名",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间"
		}],
		"schoolExperiences":[{
			"organization":"string //社团/组织名",
			"post":"string //职位",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间",
			"description":"string //说明"
		}],
		"projectExperiences":[{
			"name":"string //项目名称",
			"content":"string //担任职务",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间",
			"description":"string //说明"
		}],
		"practiceExperiences":[{
			"company":"string //公司名称",
			"post":"string //实习职位",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间",
			"description":"string //说明"
		}],
		"projectSkill":"string //项目技能",
		"honors":[{
			"name":"string //荣誉名称",
			"time":"string //获得时间",
			"description":"string //说明"
		}],
		"intro":"string //个人介绍"
	},
	"time":"string //响应时间"
}
```
## 查看当前用户简历

*作者: STEA_YY*

**请求URL**

/resume/me `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":{},
		"userId":"int //用户手机号(业务外键)",
		"name":"string //真实姓名",
		"phone":"string //手机号",
		"email":"string //电子邮箱",
		"gender":"string //性别",
		"birth":"string //出生年月",
		"highestDegree":"string //最高学历",
		"identity":"string //当前身份",
		"currentGrade":"string //当前年级",
		"workPlace":"string //期望工作地点",
		"profession":"string //期望职业",
		"eduExperiences":[{
			"degree":"string //教育层次",
			"school":"string //学校名称",
			"specialty":"string //专业",
			"grade":"string //专业排名",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间"
		}],
		"schoolExperiences":[{
			"organization":"string //社团/组织名",
			"post":"string //职位",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间",
			"description":"string //说明"
		}],
		"projectExperiences":[{
			"name":"string //项目名称",
			"content":"string //担任职务",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间",
			"description":"string //说明"
		}],
		"practiceExperiences":[{
			"company":"string //公司名称",
			"post":"string //实习职位",
			"startTime":"string //开始时间",
			"endTime":"string //结束时间",
			"description":"string //说明"
		}],
		"projectSkill":"string //项目技能",
		"honors":[{
			"name":"string //荣誉名称",
			"time":"string //获得时间",
			"description":"string //说明"
		}],
		"intro":"string //个人介绍"
	},
	"time":"string //响应时间"
}
```
## 修改当前用户简历

*作者: STEA_YY*

**请求URL**

/resume/updateResume `PUT`

**请求体**

```json
{
	"id":{},
	"userId":"int //用户手机号(业务外键)",
	"name":"string //真实姓名",
	"phone":"string //手机号",
	"email":"string //电子邮箱",
	"gender":"string //性别",
	"birth":"string //出生年月",
	"highestDegree":"string //最高学历",
	"identity":"string //当前身份",
	"currentGrade":"string //当前年级",
	"workPlace":"string //期望工作地点",
	"profession":"string //期望职业",
	"eduExperiences":[{
		"degree":"string //教育层次",
		"school":"string //学校名称",
		"specialty":"string //专业",
		"grade":"string //专业排名",
		"startTime":"string //开始时间",
		"endTime":"string //结束时间"
	}],
	"schoolExperiences":[{
		"organization":"string //社团/组织名",
		"post":"string //职位",
		"startTime":"string //开始时间",
		"endTime":"string //结束时间",
		"description":"string //说明"
	}],
	"projectExperiences":[{
		"name":"string //项目名称",
		"content":"string //担任职务",
		"startTime":"string //开始时间",
		"endTime":"string //结束时间",
		"description":"string //说明"
	}],
	"practiceExperiences":[{
		"company":"string //公司名称",
		"post":"string //实习职位",
		"startTime":"string //开始时间",
		"endTime":"string //结束时间",
		"description":"string //说明"
	}],
	"projectSkill":"string //项目技能",
	"honors":[{
		"name":"string //荣誉名称",
		"time":"string //获得时间",
		"description":"string //说明"
	}],
	"intro":"string //个人介绍"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 轮播图相关请求
## 获取全部启用的轮播图资源

*作者: STEA_YY*

**请求URL**

/slideShow/getAllSlideShowResourceEnabled `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //主键",
		"pictureUrl":"string //轮播图图片url",
		"linkType":"int //链接类型",
		"linkUrl":"string //轮播图链接路径",
		"enabled":"boolean //是否启用",
		"uploadTime":"date //轮播图上传时间"
	}],
	"time":"string //响应时间"
}
```
# 短信业务相关请求
## 发送短信验证码

*作者: STEA_YY*

**请求URL**

/sms/code `POST`

**请求体**

```json
{
	"phone":"string //手机号【必须】",
	"type":"string //验证短信类型。取值register、changePassword、bindPhone【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 用户信息相关请求
## 获取当前用户信息（包括全部详细信息和部分私有信息）

*作者: STEA_YY*

**请求URL**

/userInfo/me `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"signedNotice":"boolean //是否签署服务协议",
		"signedContract":"boolean //是否签署合同",
		"authenticated":"int //是否通过身份认证",
		"ahaCredit":"double //aha币数量",
		"ahaPoint":"double //aha点数量",
		"role":{
			"id":"int //角色id",
			"name":"string //角色名称"
		},
		"oauths":[{
			"oauthType":"string //授权类型",
			"oauthId":"string //授权码"
		}],
		"userInfo":{
			"userId":"int //用户id(外键)",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"birthday":"date //用户出生日期",
			"typeId":"int //用户类别",
			"signature":"string //用户个性签名",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"academy":"string //用户学院",
			"major":"string //用户主修专业",
			"grade":"int //用户当前年级",
			"intro":"string //用户自我介绍",
			"specialtyTags":"string //用户特长标签",
			"trueName":"string //用户真实姓名",
			"compTags":"string //用户参与过比赛标签",
			"vipLevelId":"int //用户VIP等级(外键)"
		}
	},
	"time":"string //响应时间"
}
```
## 修改当前用户详细信息

*作者: STEA_YY*

**请求URL**

/userInfo/me `PUT`

**请求体**

```json
{
	"userId":"int //用户id(外键)",
	"nickname":"string //用户昵称",
	"gender":"boolean //用户性别",
	"birthday":"date //用户出生日期",
	"typeId":"int //用户类别",
	"signature":"string //用户个性签名",
	"avatarUrl":"string //用户头像文件保存路径",
	"school":"string //用户学校",
	"academy":"string //用户学院",
	"major":"string //用户主修专业",
	"grade":"int //用户当前年级",
	"intro":"string //用户自我介绍",
	"specialtyTags":"string //用户特长标签",
	"trueName":"string //用户真实姓名",
	"compTags":"string //用户参与过比赛标签",
	"vipLevelId":"int //用户VIP等级(外键)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 通过用户id查询用户详细信息

*作者: STEA_YY*

**请求URL**

/userInfo/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"userId":"int //用户id(外键)",
		"nickname":"string //用户昵称",
		"gender":"boolean //用户性别",
		"birthday":"date //用户出生日期",
		"typeId":"int //用户类别",
		"signature":"string //用户个性签名",
		"avatarUrl":"string //用户头像文件保存路径",
		"school":"string //用户学校",
		"academy":"string //用户学院",
		"major":"string //用户主修专业",
		"grade":"int //用户当前年级",
		"intro":"string //用户自我介绍",
		"specialtyTags":"string //用户特长标签",
		"trueName":"string //用户真实姓名",
		"compTags":"string //用户参与过比赛标签",
		"vipLevelId":"int //用户VIP等级(外键)"
	},
	"time":"string //响应时间"
}
```
## 获取向COS上传公共文件签名，用于上传用户头像

*作者: STEA_YY*

**请求URL**

/userInfo/avatar/sign/upload `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
filename|string|否|文件名(要上传的文件的全名)

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
# 用户统计信息类请求
## 获取指定用户统计信息

*作者: STEA_YY*

**请求URL**

/statistics/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"userId":"int //主键",
		"totalContribPoint":"double //累计获取贡献点",
		"totalProject":"int //累计参与项目数",
		"totalReceivedCollection":"int //累计获得的收藏",
		"totalPurchasedCount":"int //累计被购买次数"
	},
	"time":"string //响应时间"
}
```
## 获取用户个人统计信息

*作者: STEA_YY*

**请求URL**

/statistics/me `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"userId":"int //主键",
		"totalContribPoint":"double //累计获取贡献点",
		"totalProject":"int //累计参与项目数",
		"totalReceivedCollection":"int //累计获得的收藏",
		"totalPurchasedCount":"int //累计被购买次数"
	},
	"time":"string //响应时间"
}
```
# 活动后台管理类请求
## 新建活动

*作者: STEA_YY*

**请求URL**

/admin/activity/saveActivity `POST`

**请求体**

```json
{
	"title":"string //活动标题【必须】",
	"intro":"string //活动介绍【必须】",
	"startTime":"date //活动开始时间【必须】",
	"endTime":"date //活动结束时间【必须】",
	"exchangeAhaPoint":"double //兑换aha点数额【必须】",
	"exchangeAhaCredit":"double //兑换aha币数额【必须】",
	"codeSum":"int //活动最大允许兑换券数量【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除活动

*作者: STEA_YY*

**请求URL**

/admin/activity/{activityId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
activityId|int|否|活动id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 生成指定数量兑换码

*作者: STEA_YY*

**请求URL**

/admin/activity/code `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
activityId|int|是|活动id
count|int|是|生成数量

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"string[] //响应数据",
	"time":"string //响应时间"
}
```
## 获取当前已发放兑换码数量

*作者: STEA_YY*

**请求URL**

/admin/activity/code/count `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
activityId|int|是|活动id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
# 竞赛后台管理类请求
## 保存竞赛信息

*作者: STEA_YY*

**请求URL**

/admin/competition/ `POST`

**请求体**

```json
{
	"id":"int",
	"typeId":"int //竞赛类别id(外键)",
	"level":"int //竞赛级别",
	"name":"string //赛事名称",
	"intro":"string //赛事简介",
	"picUrl":"int //赛事图片保存路径"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 根据竞赛id更新竞赛信息

*作者: STEA_YY*

**请求URL**

/admin/competition/{id} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|竞赛id
**请求体**

```json
{
	"id":"int",
	"typeId":"int //竞赛类别id(外键)",
	"level":"int //竞赛级别",
	"name":"string //赛事名称",
	"intro":"string //赛事简介",
	"picUrl":"int //赛事图片保存路径"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除竞赛信息

*作者: STEA_YY*

**请求URL**

/admin/competition/{id} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|竞赛id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 保存竞赛标签

*作者: STEA_YY*

**请求URL**

/admin/competition/tag `POST`

**请求体**

```json
{
	"id":"int",
	"name":"string"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 根据竞赛标签id更新竞赛标签

*作者: STEA_YY*

**请求URL**

/admin/competition/tag/{id} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|竞赛标签id
**请求体**

```json
{
	"id":"int",
	"name":"string"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除竞赛标签

*作者: STEA_YY*

**请求URL**

/admin/competition/tag/{id} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|竞赛标签id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 用户反馈后台管理相关请求
## 分页查看用户反馈

*作者: STEA_YY*

**请求URL**

/admin/feedback/getAllPersonalFeedback `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
userId|int|否|用户id
status|int|否|处理状态
type|int|否|反馈类型
lowestLevel|int|否|最低级别
highestLevel|int|否|最高级别
sortBy|string|否|排序关键字，取值time、status、type、level、replyTime
orderBy|string|否|排序方式，取值asc、desc

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int",
			"user":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"time":"date //反馈时间",
			"type":"int //反馈类型",
			"content":"string //反馈内容",
			"status":"int //反馈状态",
			"reply":"string //反馈回复",
			"replyTime":"date //反馈回复时间",
			"level":"int //反馈问题级别"
		}]
	},
	"time":"string //响应时间"
}
```
## 管理员处理用户反馈

*作者: STEA_YY*

**请求URL**

/admin/feedback/{feedbackId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
feedbackId|int|否|反馈id
**请求体**

```json
{
	"status":"int //反馈状态",
	"reply":"string //反馈回复",
	"level":"int //反馈问题级别"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 站内信后台管理类请求
## 发送群发系统消息(广播)

*作者: STEA_YY*

**请求URL**

/admin/message/systemNotice `POST`

**请求体**

```json
{
	"receiverUserId":"int //收件人用户id",
	"type":"int //类型",
	"textId":"int //信件内容id(外键)",
	"title":"string //信件标题",
	"content":"string //信件内容【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 向指定用户发送系统消息

*作者: STEA_YY*

**请求URL**

/admin/message/systemPrivate `POST`

**请求体**

```json
{
	"receiverUserId":"int //收件人用户id",
	"type":"int //类型",
	"textId":"int //信件内容id(外键)",
	"title":"string //信件标题",
	"content":"string //信件内容【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 通知后台管理类请求
## 获取全部公告

*作者: STEA_YY*

**请求URL**

/admin/notice/getAllNotice `GET`


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int",
		"title":"string //公告标题",
		"content":"string //公告内容",
		"createTime":"date //公告开始日期",
		"puttingStartTime":"date //公告投放开始日期",
		"puttingEndTime":"date //公告投放结束日期",
		"enable":"boolean //公告是否启用"
	}],
	"time":"string //响应时间"
}
```
## 发布公告

*作者: STEA_YY*

**请求URL**

/admin/notice/sendNotice `POST`

**请求体**

```json
{
	"title":"string //公告标题【必须】",
	"content":"string //公告内容【必须】",
	"puttingStartTime":"date //公告投放开始日期【必须】",
	"puttingEndTime":"date //公告投放结束日期【必须】",
	"enable":"boolean //公告是否启用【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改公告

*作者: STEA_YY*

**请求URL**

/admin/notice/{noticeId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
noticeId|int|否|公告id
**请求体**

```json
{
	"title":"string //公告标题【必须】",
	"content":"string //公告内容【必须】",
	"puttingStartTime":"date //公告投放开始日期【必须】",
	"puttingEndTime":"date //公告投放结束日期【必须】",
	"enable":"boolean //公告是否启用【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 项目后台管理类请求
## 分页获取所有项目粗略信息

*作者: STEA_YY*

**请求URL**

/admin/project/getAllProjectPageable `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
userId|int|否|用户id
compId|int|否|竞赛id
awardLevel|int|否|获奖级别
sortBy|string|否|排序关键字
orderBy|string|否|排序方式
passed|boolean|否|是否通过审核

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //项目id",
			"creatorUser":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"name":"string //项目名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
			"tags":"string //项目标签",
			"compId":"int //赛事id(外键)",
			"awardLevel":"int //项目获奖级别",
			"awardTime":"date //项目获奖时间",
			"passed":"boolean //是否通过审核"
		}]
	},
	"time":"string //响应时间"
}
```
## 按条件分页获取项目资源

*作者: STEA_YY*

**请求URL**

/admin/project/resource `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
projectId|int|否|项目id
resourcePassed|boolean|否|资源是否通过审核
projectPassed|boolean|否|资源所在项目是否通过审核

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //项目资源id",
			"projectId":"int //项目id(外键)",
			"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
			"fileType":"int //资源文件类型",
			"typeId":"int //资源类型id(外键)",
			"filename":"string //保存在oss里的资源文件名(包括前缀)",
			"previewUrl":"string //保存在oss里的预览文件地址",
			"download":"int //资源文件下载量",
			"score":"double //资源平均分",
			"scoreCount":"int //评分人数",
			"price":"double //资源价格",
			"discount":"double //资源折扣",
			"passed":"boolean //是否通过审核"
		}]
	},
	"time":"string //响应时间"
}
```
## 根据项目id获取所有项目资源

*作者: STEA_YY*

**请求URL**

/admin/project/{projectId}/resources `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //项目资源id",
		"projectId":"int //项目id(外键)",
		"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
		"fileType":"int //资源文件类型",
		"typeId":"int //资源类型id(外键)",
		"filename":"string //保存在oss里的资源文件名(包括前缀)",
		"previewUrl":"string //保存在oss里的预览文件地址",
		"download":"int //资源文件下载量",
		"score":"double //资源平均分",
		"scoreCount":"int //评分人数",
		"price":"double //资源价格",
		"discount":"double //资源折扣",
		"passed":"boolean //是否通过审核"
	}],
	"time":"string //响应时间"
}
```
## 获取项目资源文件COS下载签名

*作者: STEA_YY*

**请求URL**

/admin/project/resource/{projectResourceId}/sign/download `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"authorization":"string //上传签名",
		"expire":"long //签名过期时间戳",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 管理员获取COS私有资源上传签名(用于上传资源文件)

*作者: STEA_YY*

**请求URL**

/admin/project/{projectId}/resources/sign/upload/private `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
filename|string|否|待上传文件名

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"policy":"string //经过 Base64 编码的“策略”（Policy）内容",
		"secretId":"string //COS SecretId",
		"keyTime":"string //COS KeyTime",
		"signature":"string //上传签名",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 管理员新建项目并指定作者用户

*作者: STEA_YY*

**请求URL**

/admin/project/saveProjectAndAuthor `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
creatorUserId|int|是|创建者用户id
**请求体**

```json
{
	"name":"string //团队名称【必须】",
	"avatarUrl":"string //团队头像url",
	"compId":"int //赛事id(外键)【必须】",
	"compName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
	"awardLevel":"int //项目获奖级别",
	"tags":"string //项目标签",
	"awardTime":"date //项目获奖时间",
	"awardProveUrl":"string //获奖证明文件url",
	"intro":"string //团队介绍(富文本)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
## 管理员新增项目资源

*作者: STEA_YY*

**请求URL**

/admin/project/resource/{projectId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
{
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)【必须】",
	"fileType":"int //资源文件类型【必须】",
	"typeId":"int //资源类型id(外键)【必须】",
	"filename":"string //保存在oss里的资源文件名(包括前缀)【必须】",
	"price":"double //资源价格【必须】",
	"discount":"double //资源折扣【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"int //响应数据",
	"time":"string //响应时间"
}
```
## 管理员创建项目成员

*作者: STEA_YY*

**请求URL**

/admin/project/member/{projectId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
{
	"projectId":"int",
	"memberUserId":"int //团队成员id【必须】",
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)【必须】",
	"job":"string //团队成员职务【必须】",
	"editable":"boolean //成员是否可编辑项目信息【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 审核项目

*作者: STEA_YY*

**请求URL**

/admin/project/check/{projectId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
{
	"meaning":"double //项目资源完整程度，决定贡献点【必须】",
	"passed":"boolean //是否通过审核【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 审核项目资源

*作者: STEA_YY*

**请求URL**

/admin/project/resource/check/{resourceId} `POST`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
resourceId|int|否|项目资源id
**请求体**

```json
{
	"passed":"boolean【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 分页获取项目购买记录

*作者: STEA_YY*

**请求URL**

/admin/project/resource/purchased `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
resourceId|int|否|项目资源id
projectId|int|否|项目id
userId|int|否|用户id
orderId|int|否|订单id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"userId":"int //用户id",
			"resourceId":"int //资源id",
			"purchaseTime":"date //购买时间",
			"contribPointOrder":{
				"id":"int //订单号",
				"user":{
					"userId":"int //用户id",
					"nickname":"string //用户昵称",
					"gender":"boolean //用户性别",
					"avatarUrl":"string //用户头像文件保存路径",
					"school":"string //用户学校",
					"vipLevelId":"int //用户VIP等级(外键)"
				},
				"project":{
					"id":"int //项目id",
					"creatorUser":{
						"userId":"int //用户id",
						"nickname":"string //用户昵称",
						"gender":"boolean //用户性别",
						"avatarUrl":"string //用户头像文件保存路径",
						"school":"string //用户学校",
						"vipLevelId":"int //用户VIP等级(外键)"
					},
					"name":"string //项目名称",
					"read":"int //点击率",
					"collect":"int //收藏数量",
					"avatarUrl":"string //团队头像url",
					"tags":"string //项目标签",
					"compId":"int //赛事id(外键)",
					"awardLevel":"int //项目获奖级别",
					"awardTime":"date //项目获奖时间",
					"passed":"boolean //是否通过审核"
				},
				"totalCost":"double //订单总价",
				"status":"int //订单状态",
				"createTime":"date //订单创建时间",
				"payTime":"date //订单支付时间",
				"orderResources":[{
					"resource":{
						"id":"int //项目资源id",
						"projectId":"int //项目id(外键)",
						"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
						"fileType":"int //资源文件类型",
						"typeId":"int //资源类型id(外键)",
						"filename":"string //保存在oss里的资源文件名(包括前缀)",
						"previewUrl":"string //保存在oss里的预览文件地址",
						"download":"int //资源文件下载量",
						"score":"double //资源平均分",
						"scoreCount":"int //评分人数",
						"price":"double //资源价格",
						"discount":"double //资源折扣",
						"passed":"boolean //是否通过审核"
					},
					"discount":"double //资源折扣",
					"price":"double //贡献点小计"
				}],
				"chargedAhaCredit":"double //订单实际支付aha币",
				"chargedAhaPoint":"double //订单实际支付aha点"
			}
		}]
	},
	"time":"string //响应时间"
}
```
## 修改项目详细信息

*作者: STEA_YY*

**请求URL**

/admin/project/{projectId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
{
	"name":"string //团队名称【必须】",
	"avatarUrl":"string //团队头像url",
	"compId":"int //赛事id(外键)【必须】",
	"compName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
	"awardLevel":"int //项目获奖级别",
	"tags":"string //项目标签",
	"awardTime":"date //项目获奖时间",
	"awardProveUrl":"string //获奖证明文件url",
	"intro":"string //团队介绍(富文本)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除项目

*作者: STEA_YY*

**请求URL**

/admin/project/{projectId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改项目成员信息

*作者: STEA_YY*

**请求URL**

/admin/project/member/{projectId}/{memberUserId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
memberUserId|int|否|项目成员用户id
**请求体**

```json
{
	"projectId":"int",
	"memberUserId":"int //团队成员id【必须】",
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)【必须】",
	"job":"string //团队成员职务【必须】",
	"editable":"boolean //成员是否可编辑项目信息【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改多个项目成员信息

*作者: STEA_YY*

**请求URL**

/admin/project/members/{projectId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
**请求体**

```json
[{
	"projectId":"int",
	"memberUserId":"int //团队成员id【必须】",
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)【必须】",
	"job":"string //团队成员职务【必须】",
	"editable":"boolean //成员是否可编辑项目信息【必须】"
}]
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除项目成员

*作者: STEA_YY*

**请求URL**

/admin/project/member/{projectId}/{memberUserId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
memberUserId|int|否|成员用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改项目资源信息

*作者: STEA_YY*

**请求URL**

/admin/project/resource/{projectResourceId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id
**请求体**

```json
{
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
	"fileType":"int //资源文件类型",
	"typeId":"int //资源类型id(外键)",
	"price":"double //资源价格",
	"discount":"double //资源折扣"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除项目资源

*作者: STEA_YY*

**请求URL**

/admin/project/resource/{projectResourceId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除资源评论

*作者: STEA_YY*

**请求URL**

/admin/project/resource/score/{projectResourceId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectResourceId|int|否|项目资源id
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 轮播图后台管理类请求
## 获取指定轮播图资源

*作者: STEA_YY*

**请求URL**

/admin/slideShow/{id} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|轮播图资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //主键",
		"pictureUrl":"string //轮播图图片url",
		"linkType":"int //链接类型",
		"linkUrl":"string //轮播图链接路径",
		"enabled":"boolean //是否启用",
		"uploadTime":"date //轮播图上传时间"
	},
	"time":"string //响应时间"
}
```
## 按条件获取全部轮播图资源

*作者: STEA_YY*

**请求URL**

/admin/slideShow/getAllSlideShowResourceEnabled `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
enabled|boolean|否|是否启用

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //主键",
		"pictureUrl":"string //轮播图图片url",
		"linkType":"int //链接类型",
		"linkUrl":"string //轮播图链接路径",
		"enabled":"boolean //是否启用",
		"uploadTime":"date //轮播图上传时间"
	}],
	"time":"string //响应时间"
}
```
## 新建轮播图资源

*作者: STEA_YY*

**请求URL**

/admin/slideShow/saveSlideShowResource `POST`

**请求体**

```json
{
	"pictureUrl":"string //轮播图图片url【必须】",
	"linkType":"int //链接类型【必须】",
	"linkUrl":"string //轮播图链接路径【必须】",
	"enabled":"boolean //是否启用【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 更新轮播图资源

*作者: STEA_YY*

**请求URL**

/admin/slideShow/{id} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|轮播图资源id
**请求体**

```json
{
	"pictureUrl":"string //轮播图图片url【必须】",
	"linkType":"int //链接类型【必须】",
	"linkUrl":"string //轮播图链接路径【必须】",
	"enabled":"boolean //是否启用【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除轮播图资源

*作者: STEA_YY*

**请求URL**

/admin/slideShow/{id} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|轮播图资源id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
# 用户后台管理类请求
## 分页获取所有用户信息

*作者: STEA_YY*

**请求URL**

/admin/user/getAllUserManagementVoPagable `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页码
pageSize|int|否|分页大小
roleId|int|否|角色id
signedNotice|boolean|否|是否签署服务协议
signedContract|boolean|否|是否签署合同
typeId|int|否|用户类型
nicknameLike|string|否|模糊昵称
trueNameLike|string|否|模糊真实姓名
sortBy|string|否|排序关键字
orderBy|string|否|排序方式

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"id":"int //用户id",
			"createdTime":"date //账户首次登录时间",
			"studentRecFilename":"string //学生证图片保存路径",
			"ahaCredit":"double //aha币数量",
			"ahaPoint":"double //aha点数量",
			"signedNotice":"boolean //是否签署服务协议",
			"signedContract":"boolean //是否签署合同",
			"authenticated":"int //是否通过身份认证",
			"roleId":"int //角色id(外键)",
			"role":{
				"id":"int //角色id",
				"name":"string //角色名称"
			},
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"birthday":"date //用户出生日期",
			"typeId":"int //用户类别",
			"signature":"string //用户个性签名",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"academy":"string //用户学院",
			"major":"string //用户主修专业",
			"grade":"int //用户当前年级",
			"intro":"string //用户自我介绍",
			"specialtyTags":"string //用户特长标签",
			"trueName":"string //用户真实姓名",
			"compTags":"string //用户参与过比赛标签"
		}]
	},
	"time":"string //响应时间"
}
```
## 获取用户信息

*作者: STEA_YY*

**请求URL**

/admin/user/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //用户id",
		"createdTime":"date //账户首次登录时间",
		"studentRecFilename":"string //学生证图片保存路径",
		"ahaCredit":"double //aha币数量",
		"ahaPoint":"double //aha点数量",
		"signedNotice":"boolean //是否签署服务协议",
		"signedContract":"boolean //是否签署合同",
		"authenticated":"int //是否通过身份认证",
		"roleId":"int //角色id(外键)",
		"role":{
			"id":"int //角色id",
			"name":"string //角色名称"
		},
		"nickname":"string //用户昵称",
		"gender":"boolean //用户性别",
		"birthday":"date //用户出生日期",
		"typeId":"int //用户类别",
		"signature":"string //用户个性签名",
		"avatarUrl":"string //用户头像文件保存路径",
		"school":"string //用户学校",
		"academy":"string //用户学院",
		"major":"string //用户主修专业",
		"grade":"int //用户当前年级",
		"intro":"string //用户自我介绍",
		"specialtyTags":"string //用户特长标签",
		"trueName":"string //用户真实姓名",
		"compTags":"string //用户参与过比赛标签"
	},
	"time":"string //响应时间"
}
```
## 修改用户私有信息

*作者: STEA_YY*

**请求URL**

/admin/user/user/{userId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id
**请求体**

```json
{
	"studentRecFilename":"string //学生证图片保存路径",
	"contribPoint":"double //贡献点",
	"signedNotice":"boolean //是否签署服务协议",
	"signedContract":"boolean //是否签署合同",
	"roleId":"int //角色id(外键)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 修改用户公开信息

*作者: STEA_YY*

**请求URL**

/admin/user/info/{userId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id
**请求体**

```json
{
	"userId":"int //用户id(外键)",
	"nickname":"string //用户昵称",
	"gender":"boolean //用户性别",
	"birthday":"date //用户出生日期",
	"typeId":"int //用户类别",
	"signature":"string //用户个性签名",
	"avatarUrl":"string //用户头像文件保存路径",
	"school":"string //用户学校",
	"academy":"string //用户学院",
	"major":"string //用户主修专业",
	"grade":"int //用户当前年级",
	"intro":"string //用户自我介绍",
	"specialtyTags":"string //用户特长标签",
	"trueName":"string //用户真实姓名",
	"compTags":"string //用户参与过比赛标签",
	"vipLevelId":"int //用户VIP等级(外键)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 删除用户

*作者: STEA_YY*

**请求URL**

/admin/user/{userId} `DELETE`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 获取用户合同信息

*作者: STEA_YY*

**请求URL**

/admin/user/contract/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int",
		"userId":"int //用户id(外键)",
		"name":"string //联系人",
		"signatureFilename":"string //签名文件名",
		"signTime":"date //合同签名时间"
	},
	"time":"string //响应时间"
}
```
## 下载用户合同签名

*作者: STEA_YY*

**请求URL**

/admin/user/contract/signature/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 分页获取身份认证信息

*作者: STEA_YY*

**请求URL**

/admin/user/authentication `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
pageNum|int|否|页面
pageSize|int|否|分页大小
status|int|否|审核状态
type|int|否|身份认证类型

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"pageNum":"int //页码",
		"pageSize":"int //分页大小",
		"pageData":[{
			"user":{
				"userId":"int //用户id",
				"nickname":"string //用户昵称",
				"gender":"boolean //用户性别",
				"avatarUrl":"string //用户头像文件保存路径",
				"school":"string //用户学校",
				"vipLevelId":"int //用户VIP等级(外键)"
			},
			"trueName":"string //真实姓名",
			"type":"int //认证类型(学生or社会人士)",
			"studentCardFilename":"string //学生证图片文件名",
			"idCardFrontFilename":"string //身份证正面文件名",
			"idCardBackFilename":"string //身份证背面文件名",
			"status":"int //认证审核状态",
			"uploadTime":"date //上传日期",
			"passTime":"date //审核通过日期"
		}]
	},
	"time":"string //响应时间"
}
```
## 根据用户id获取实名认证信息

*作者: STEA_YY*

**请求URL**

/admin/user/authentication/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"user":{
			"userId":"int //用户id",
			"nickname":"string //用户昵称",
			"gender":"boolean //用户性别",
			"avatarUrl":"string //用户头像文件保存路径",
			"school":"string //用户学校",
			"vipLevelId":"int //用户VIP等级(外键)"
		},
		"trueName":"string //真实姓名",
		"type":"int //认证类型(学生or社会人士)",
		"studentCardFilename":"string //学生证图片文件名",
		"idCardFrontFilename":"string //身份证正面文件名",
		"idCardBackFilename":"string //身份证背面文件名",
		"status":"int //认证审核状态",
		"uploadTime":"date //上传日期",
		"passTime":"date //审核通过日期"
	},
	"time":"string //响应时间"
}
```
## 获取身份认证照片下载签名

*作者: STEA_YY*

**请求URL**

/admin/user/authentication/file/{userId} `GET`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id
fileType|string|否|文件类型(取值:studentCard-学生证,idCardFront-身份证正面,idCardBack-身份证反面)

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"bucketName":"string //目标bucketName",
		"region":"string //服务器地域",
		"authorization":"string //上传签名",
		"expire":"long //签名过期时间戳",
		"filename":"string //完整文件名"
	},
	"time":"string //响应时间"
}
```
## 审核身份认证信息

*作者: STEA_YY*

**请求URL**

/admin/user/authentication/check/{userId} `PUT`

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
userId|int|否|用户id
status|int|否|审核结果(取值:1-通过,2-不通过)

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```

# 人才市场后台管理类请求

## 分页获取人才粗略信息列表

*作者: Qin Zhenghan*

**请求URL**

/talentMarket `GET`

**请求参数**

|  参数名  | 类型 | 必须 |   描述   |
| :------: | :--: | :--: | :------: |
| pageNum  | int  |  是  |   页码   |
| pageSize | int  |  是  | 分页大小 |

**返回结果**

```json
{
    "code": "int //响应状态码",
    "msg": "string //响应消息",
    "data": {
        "pageNum": "int //页码",
        "pageSize": "int //分页大小",
        "pageData": [
            {
                "user": {
                    "userId": "int //用户id",
                    "nickname": "string //用户昵称",
                    "gender": "boolean //用户性别",
                    "avatarUrl": "string //用户头像文件保存路径",
                    "school": "string //用户学校",
                    "vipLevelId": "string //用户VIP等级"
                },
                "contribPoint": "BigDecimal //用户贡献点",
                "rank": "long //用户贡献点排名"
            }
        ]
    },
    "time": "string //响应时间"
}
```

## 获取人才详细信息

*作者: Qin Zhenghan*

**请求URL**

/talentMarket/{userId} `GET`

**请求参数**

| 参数名 | 类型 | 必须 |  描述  |
| :----: | :--: | :--: | :----: |
| userId | int  |  是  | 用户id |

**返回结果**

```json
{
    "code": "int //响应状态码",
    "msg": "string //响应消息",
    "data": {
        "userId": "int //用户id",
        "nickname": "string //用户昵称",
        "gender": "boolean //用户性别",
        "birthday": "string //用户头像文件保存路径",
        "avatarUrl": "string //用户头像文件保存路径",
        "school": "string //用户学校",
        "academy": "string //用户学院",
        "major": "string //用户主修专业",
		"grade": "int //用户当前年级",
		"intro": "string //用户自我介绍",
        "specialtyTags":"string //用户特长标签",
		"trueName": "string //用户真实姓名",
		"compTags": "string //用户参与过比赛标签",
        "oauths": "Oauths //鉴权对象",
        "userInfo": "UserInfo //UserInfo对象"
    },
    "time": "string //响应时间"
}
```

