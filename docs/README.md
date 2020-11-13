# 授权鉴权类请求

## 通过账号密码登录

*作者: STEA_YY*

**请求URL**

/login `POST` 

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
			"phone":"string //手机号",
			"signedNotice":"boolean //是否签署服务协议",
			"signedContract":"boolean //是否签署合同",
			"contribPoint":"double //贡献点",
			"userInfo":{
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

/register `POST` 

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
			"phone":"string //手机号",
			"signedNotice":"boolean //是否签署服务协议",
			"signedContract":"boolean //是否签署合同",
			"contribPoint":"double //贡献点",
			"userInfo":{
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
## 修改密码

*作者: STEA_YY*

**请求URL**

/changePassword/{phone} `POST` 

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

/sign/notice `GET` 


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
signatureFilename|string|否|签名文件名

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
		"competitionTag":{
			"id":"int //竞赛标签id",
			"name":"string //竞赛标签名称"
		},
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
		"competitionTag":{
			"id":"int //竞赛标签id",
			"name":"string //竞赛标签名称"
		},
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
## 保存竞赛信息

*作者: STEA_YY*

**请求URL**

/competition `POST` 

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

/competition/{id} `PUT` 

**请求参数**

| 参数名 | 类型 | 必须 | 描述   |
| -----: | :--: | :--: | :----- |
|     id | int  |  否  | 竞赛id |

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

## 保存竞赛标签

*作者: STEA_YY*

**请求URL**

/competition/tag `POST` 

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

/competition/tag/{id} `PUT` 

**请求参数**

| 参数名 | 类型 | 必须 | 描述       |
| -----: | :--: | :--: | :--------- |
|     id | int  |  否  | 竞赛标签id |

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
		"phone":"string //手机号",
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
		"phone":"string //手机号",
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
# 项目相关请求
## 获取全部项目粗略信息

*作者: STEA_YY*

**请求URL**

/project `GET` 


**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":[{
		"id":"int //项目id",
		"creatorPhone":"string //团队创建者手机号",
		"name":"string //团队名称",
		"read":"int //点击率",
		"collect":"int //收藏数量",
		"avatarUrl":"string //团队头像url"
	}],
	"time":"string //响应时间"
}
```
## 根据项目id获取项目粗略信息

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
		"creatorPhone":"string //团队创建者手机号",
		"name":"string //团队名称",
		"read":"int //点击率",
		"collect":"int //收藏数量",
		"avatarUrl":"string //团队头像url"
	},
	"time":"string //响应时间"
}
```
## 获取oss公开资源上传签名(用于上传项目头像)

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
	"awardName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
	"awardLevel":"int //项目获奖级别",
	"awardTime":"date //项目获奖时间",
	"intro":"string //团队介绍(富文本)"
}
```

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
		"projectId":"int //项目id(外键)",
		"project":{
			"id":"int //项目id",
			"creatorPhone":"string //团队创建者手机号",
			"name":"string //团队名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"meaning":"double //项目资源完整程度，决定贡献点",
			"avatarUrl":"string //团队头像url",
			"passed":"boolean //项目是否通过审核，公开共享"
		},
		"compId":"int //赛事id(外键)",
		"competition":{
			"id":"int //竞赛id",
			"compTagId":"int //所属赛事标签（外键）",
			"competitionTag":{
				"id":"int //竞赛标签id",
				"name":"string //竞赛标签名称"
			},
			"name":"string //赛事名称",
			"intro":"string //赛事简介",
			"picUrl":"int //赛事图片保存路径"
		},
		"awardName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
		"awardLevel":"int //项目获奖级别",
		"awardTime":"date //项目获奖时间",
		"awardProveUrl":"string //获奖证明文件url",
		"intro":"string //团队介绍(富文本)",
		"members":[{
			"projectId":"int //项目id(外键)",
			"memberPhone":"string //团队成员手机号",
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
			"projectId":"int //团队id(外键)",
			"type":"int //资源文件类别",
			"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
			"filename":"string //保存在oss里的资源文件名(包括前缀)",
			"download":"int //资源文件下载量"
		}]
	},
	"time":"string //响应时间"
}
```

## 修改项目粗略信息

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
## 根据项目id获取项目详细信息

*作者: STEA_YY*

**请求URL**

/project/{projectId}/info `GET` 

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
		"projectId":"int //项目id(外键)",
		"compId":"int //赛事id(外键)",
		"competition":{
			"id":"int //竞赛id",
			"compTagId":"int //所属赛事标签（外键）",
			"competitionTag":{
				"id":"int //竞赛标签id",
				"name":"string //竞赛标签名称"
			},
			"name":"string //赛事名称",
			"intro":"string //赛事简介",
			"picUrl":"int //赛事图片保存路径"
		},
		"awardName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
		"awardLevel":"int //项目获奖级别",
		"awardTime":"date //项目获奖时间",
		"intro":"string //团队介绍(富文本)",
		"members":[{
			"memberPhone":"string //团队成员手机号",
			"nickname":"string",
			"trueName":"string",
			"avatarUrl":"string",
			"school":"string",
			"rank":"int //团队成员顺位(决定显示顺序，1为队长)",
			"job":"string //团队成员职务",
			"editable":"boolean //成员是否可编辑项目信息"
		}],
		"resources":[{
			"id":"int //项目资源id",
			"type":"int //资源文件类别",
			"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
			"filename":"string //保存在oss里的资源文件名(包括前缀)",
			"download":"int //资源文件下载量"
		}]
	},
	"time":"string //响应时间"
}
```
## 修改项目详细信息

*作者: STEA_YY*

**请求URL**

/project/info/{projectId} `PUT` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id

**请求体**

```json
{
	"compId":"int //赛事id(外键)",
	"awardName":"string //比赛和获奖全名(如中国大学生服务外包创新创业大赛全国一等奖)",
	"awardLevel":"int //项目获奖级别",
	"awardTime":"date //项目获奖时间",
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
## 根据项目id获取所有项目成员

*作者: STEA_YY*

**请求URL**

/project/{projectId}/member `GET` 

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
		"memberPhone":"string //团队成员手机号",
		"nickname":"string",
		"trueName":"string",
		"avatarUrl":"string",
		"school":"string",
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
	"memberPhone":"string //团队成员手机号【必须】",
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

/project/member/{projectId}/{memberPhone} `PUT` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
memberPhone|string|否|成员手机号

**请求体**

```json
{
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
## 删除项目成员

*作者: STEA_YY*

**请求URL**

/project/member/{projectId}/{memberPhone} `DELETE` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
projectId|int|否|项目id
memberPhone|string|否|成员手机号

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

/project/{projectId}/resource `GET` 

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
		"type":"int //资源文件类别",
		"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)",
		"filename":"string //保存在oss里的资源文件名(包括前缀)",
		"download":"int //资源文件下载量"
	}],
	"time":"string //响应时间"
}
```
## 获取oss私有资源上传签名(用于上传资源文件)

*作者: STEA_YY*

**请求URL**

/project/{projectId}/resource/sign/upload/private `GET` 

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
	"type":"int //资源文件类别【必须】",
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)【必须】",
	"filename":"string //保存在oss里的资源文件名(包括前缀)"
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
## 修改项目资源

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
	"type":"int //资源文件类别【必须】",
	"name":"string //资源名称(前端显示，如“城市鹰眼”智慧交通大数据挖掘系统-项目详细文档)【必须】",
	"filename":"string //保存在oss里的资源文件名(包括前缀)"
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
			"creatorPhone":"string //团队创建者手机号",
			"name":"string //团队名称",
			"read":"int //点击率",
			"collect":"int //收藏数量",
			"avatarUrl":"string //团队头像url",
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
# 用户简历相关请求
## 根据手机号查看用户简历

*作者: STEA_YY*

**请求URL**

/resume/{phone} `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phone|string|否|手机号

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
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
## 发送注册短信验证码

*作者: STEA_YY*

**请求URL**

/sms/sendCode/register/{phone} `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phone|string|否|手机号

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":"object //响应数据",
	"time":"string //响应时间"
}
```
## 发送重置密码短信验证码

*作者: STEA_YY*

**请求URL**

/sms/sendCode/changePassword/{phone} `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phone|string|否|手机号

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
		"phone":"string //手机号",
		"signedNotice":"boolean //是否签署服务协议",
		"signedContract":"boolean //是否签署合同",
		"contribPoint":"double //贡献点",
		"userInfo":{
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
## 通过手机号查询用户详细信息

*作者: STEA_YY*

**请求URL**

/userInfo/{phone} `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phone|string|否|手机号

**返回结果**

```json
{
	"code":"int //响应状态码",
	"msg":"string //响应消息",
	"data":{
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
## 修改用户头像文件名

*作者: STEA_YY*

**请求URL**

/userInfo/avatar `POST` 

**请求体**

```json
Map{}
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
# 微信小程序授权/鉴权相关请求
## 通过微信小程序授权登录

*作者: STEA_YY*

**请求URL**

/wxLogin `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
code|string|否|小程序请求码

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
