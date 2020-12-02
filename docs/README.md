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
			"contribPoint":"double //贡献点",
			"role":{
				"id":"int //角色id",
				"name":"string //角色名称"
			},
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
				"compTags":"string //用户参与过比赛标签"
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
			"contribPoint":"double //贡献点",
			"role":{
				"id":"int //角色id",
				"name":"string //角色名称"
			},
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
				"compTags":"string //用户参与过比赛标签"
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
	"data":{
		"token":"string //token令牌"
	},
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
name|string|否|联系人

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"token":"string //token令牌"
	},
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
    "avatarUrl": "string //用户头像URL",
	"code":"string //微信code【必须】"
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
			"contribPoint":"double //贡献点",
			"role":{
				"id":"int //角色id",
				"name":"string //角色名称"
			},
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
				"compTags":"string //用户参与过比赛标签"
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
		"compTagId":"int //所属赛事标签（外键）",
		"name":"string //赛事名称",
		"intro":"string //赛事简介",
		"picUrl":"int //赛事图片保存路径"
	},
	"time":"string //响应时间"
}
```
## 获取所有竞赛信息

*作者: STEA_YY*

**请求URL**

/competition `GET` 


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //竞赛id",
		"compTagId":"int //所属赛事标签（外键）",
		"name":"string //赛事名称",
		"intro":"string //赛事简介",
		"picUrl":"int //赛事图片保存路径"
	}],
	"time":"string //响应时间"
}
```
## 根据id获取竞赛标签

*作者: STEA_YY*

**请求URL**

/competition/tag/{id} `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
id|int|否|竞赛标签id

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //竞赛标签id",
		"name":"string //竞赛标签名称"
	},
	"time":"string //响应时间"
}
```
## 获取所有竞赛标签

*作者: STEA_YY*

**请求URL**

/competition/tag `GET` 


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //竞赛标签id",
		"name":"string //竞赛标签名称"
	}],
	"time":"string //响应时间"
}
```
# 贡献点排名相关请求
## 获取贡献点总排行榜

*作者: STEA_YY*

**请求URL**

/rank `GET` 


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"userId":"int //用户id",
		"contribPoint":"double //贡献点",
		"rank":"long //排名"
	}],
	"time":"string //响应时间"
}
```
## 获取用户个人排名

*作者: STEA_YY*

**请求URL**

/rank/me `GET` 


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"userId":"int //用户id",
		"contribPoint":"double //贡献点",
		"rank":"long //排名"
	},
	"time":"string //响应时间"
}
```
# 文件下载相关请求
## 获取oss签名，已弃用

*作者: STEA_YY*

**请求URL**

/file/{filename} `GET` 

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
		"url":"string //url字符串"
	},
	"time":"string //响应时间"
}
```
# 后台管理相关请求
## 获取项目资源文件oss下载签名

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
		"url":"string //url字符串"
	},
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
	"meaning":"double //项目资源完整程度，决定贡献点",
	"passed":"boolean //是否通过审核"
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
	"id":"int //项目资源id",
	"projectId":"int //团队id(外键)",
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
	"filename":"string //保存在oss里的资源文件名(包括前缀)",
	"previewUrl":"string //保存在oss里的预览文件地址",
	"download":"int //资源文件下载量",
	"freezed":"boolean //资源是否被冻结"
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
## 保存竞赛信息

*作者: STEA_YY*

**请求URL**

/admin/competition `POST` 

**请求体**

```json
{
	"compTagId":"int //所属赛事标签（外键）",
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
	"compTagId":"int //所属赛事标签（外键）",
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
	"name":"string //竞赛标签名称"
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
	"name":"string //竞赛标签名称"
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
## 分页获取所有用户信息

*作者: STEA_YY*

**请求URL**

/admin/user `GET` 

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
			"contribPoint":"double //贡献点",
			"signedNotice":"boolean //是否签署服务协议",
			"signedContract":"boolean //是否签署合同",
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
		"contribPoint":"double //贡献点",
		"signedNotice":"boolean //是否签署服务协议",
		"signedContract":"boolean //是否签署合同",
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

/admin/user/{userId} `PUT` 

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
## 发送群发系统消息(广播)

*作者: STEA_YY*

**请求URL**

/admin/message/systemNotice `POST` 

**请求体**

```json
{
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

/message `GET` 

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
			"senderUserId":"int //发信人用户id",
			"type":"int //类型",
			"status":"int //阅读状态",
			"receiveDate":"date //收件时间",
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
		"senderUserId":"int //发信人用户id",
		"type":"int //类型",
		"status":"int //阅读状态",
		"receiveDate":"date //收件时间",
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
# OSS相关请求
## 内容安全扫描异常回调(OSS端发送)

*作者: STEA_YY*

**请求URL**

/oss/green/callback `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
checksum|string|否|校验信息
content|string|否|返回实体(json)

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
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
			"creatorUserId":"int //团队创建者用户id",
			"name":"string //项目名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
			"tags":"string //项目标签",
			"compId":"int //赛事id(外键)",
			"awardLevel":"int //项目获奖级别",
			"awardTime":"date //项目获奖时间"
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
		"creatorUserId":"int //团队创建者用户id",
		"name":"string //团队名称",
		"avatarUrl":"string //团队头像url",
		"tags":"string //项目标签",
		"intro":"string //团队介绍(富文本)",
		"read":"int //点击率",
		"collect":"int //收藏数量",
		"compId":"int //赛事id(外键)",
		"competition":{
			"id":"int //竞赛id",
			"compTagId":"int //所属赛事标签（外键）",
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
			"memberUserId":"int //团队成员用户id",
			"nickname":"string //成员昵称",
			"trueName":"string //成员真实姓名",
			"avatarUrl":"string //成员头像url",
			"school":"string //成员所在学校",
			"rank":"int //团队成员顺位(决定显示顺序，1为队长)",
			"job":"string //团队成员职务",
			"editable":"boolean //成员是否可编辑项目信息"
		}],
		"resources":[{
			"id":"int //项目资源id",
			"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
			"filename":"string //保存在oss里的资源文件名(包括前缀)",
			"previewUrl":"string //保存在oss里的预览文件地址",
			"download":"int //资源文件下载量"
		}]
	},
	"time":"string //响应时间"
}
```
## 获取oss公开资源上传签名(用于上传项目头像和获奖证明材料)

*作者: STEA_YY*

**请求URL**

/project/sign/upload/public `GET` 


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"accessid":"string //阿里云accessid",
		"host":"string //oss服务器地址",
		"policy":"string //上传保险",
		"signature":"string //上传签名",
		"expire":"long //签名过期时间戳",
		"dir":"string //上传路径(文件前缀)"
	},
	"time":"string //响应时间"
}
```
## 新增项目

*作者: STEA_YY*

**请求URL**

/project `POST` 

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
	"data":{
		"id":"int //插入后的id"
	},
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
		"memberUserId":"int //团队成员用户id",
		"nickname":"string //成员昵称",
		"trueName":"string //成员真实姓名",
		"avatarUrl":"string //成员头像url",
		"school":"string //成员所在学校",
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
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)",
	"job":"string //团队成员职务",
	"editable":"boolean //成员是否可编辑项目信息"
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
	"memberUserId":"int //团队成员id【必须】",
	"rank":"int //团队成员顺位(决定显示顺序，1为队长)",
	"job":"string //团队成员职务",
	"editable":"boolean //成员是否可编辑项目信息"
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

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //项目资源id",
		"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
		"filename":"string //保存在oss里的资源文件名(包括前缀)",
		"previewUrl":"string //保存在oss里的预览文件地址",
		"download":"int //资源文件下载量"
	}],
	"time":"string //响应时间"
}
```
## 获取oss私有资源上传签名(用于上传资源文件)

*作者: STEA_YY*

**请求URL**

/project/{projectId}/resources/sign/upload/private `GET` 

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
		"accessid":"string //阿里云accessid",
		"host":"string //oss服务器地址",
		"policy":"string //上传保险",
		"signature":"string //上传签名",
		"expire":"long //签名过期时间戳",
		"dir":"string //上传路径(文件前缀)"
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
	"filename":"string //保存在oss里的资源文件名(包括前缀)【必须】"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"id":"int //插入后的id"
	},
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
## 获取项目资源文件oss下载签名

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
		"url":"string //url字符串"
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
			"creatorUserId":"int //团队创建者用户id",
			"name":"string //项目名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
			"tags":"string //项目标签",
			"compId":"int //赛事id(外键)",
			"awardLevel":"int //项目获奖级别",
			"awardTime":"date //项目获奖时间"
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

/project/collection/{projectId} `GET` 

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

/resume `PUT` 

**请求体**

```json
{
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
		"contribPoint":"double //贡献点",
		"role":{
			"id":"int //角色id",
			"name":"string //角色名称"
		},
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
			"compTags":"string //用户参与过比赛标签"
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
		"compTags":"string //用户参与过比赛标签"
	},
	"time":"string //响应时间"
}
```
## 获取向OSS上传公共文件签名，用于上传用户头像

*作者: STEA_YY*

**请求URL**

/userInfo/avatar/sign/upload `GET` 


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"accessid":"string //阿里云accessid",
		"host":"string //oss服务器地址",
		"policy":"string //上传保险",
		"signature":"string //上传签名",
		"expire":"long //签名过期时间戳",
		"dir":"string //上传路径(文件前缀)"
	},
	"time":"string //响应时间"
}
```