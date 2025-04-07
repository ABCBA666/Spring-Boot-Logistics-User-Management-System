# Spring Boot Logistics User Management System

这是一个基于Spring Boot的物流用户管理系统的初版，简单容易上手，提供用户认证和权限管理功能。

## 功能特性

- 用户认证
  - 登录
  - 注册
  - 密码验证
- 用户管理
  - 查询用户信息
  - 更新用户信息
  - 删除用户
- 角色与权限
  - 角色分配
  - 权限查询
- 操作日志
  - 自动记录用户操作
  - 详细的操作追踪

## 技术栈

- Java 11
- Spring Boot 2.7.0
- MyBatis
- MySQL 8.0
- JWT 0.7.0
- Maven

## 快速开始

### 前置要求

- JDK 11 或更高版本
- Maven 3.x
- MySQL 8.0

### 配置数据库

1. 在MySQL中创建数据库，可以导入login.sql文件
※ 每次运行之前，需要在navicat中打开数据库，避免报错
2. 修改 `src/main/resources/application.properties` 中的数据库配置：
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 构建和运行
应用将在 `http://localhost:8082` 启动，`src/main/resources/application.properties` 文件中的端口号可以修改

## API 接口

### 用户认证
- POST `/api/login` - 用户登录
- POST `/api/register` - 用户注册

### 用户管理
- GET `/api/user/{uid}` - 获取用户信息
- PUT `/api/update/{uid}` - 更新用户信息
- DELETE `/api/user/{uid}` - 删除用户

### 角色与权限
- POST `/api/user/{uid}/role` - 分配用户角色
- GET `/api/user/{uid}/permissions` - 获取用户权限

### 日志记录
- PUT `/api/updatelog/{uid}` - 带日志记录的用户信息更新

## 响应格式

Postman->Body->raw,复制粘贴即可
```json
{
    "uname": "yy",      
    "pwd": "23333"
}
```
所有API返回统一的JSON格式：状态码、状态码、token

```json
{
    "code": 200,
    "msg": "操作成功",
    "token": ""
}
```

状态码说明：
- 200: 操作成功
- 100: 参数错误
- 500: 操作失败

## Postman 测试
![img](https://github.com/user-attachments/assets/70d1101c-f9e0-40bb-b904-4e4eb3d002e3)
![img_1](https://github.com/user-attachments/assets/084e0566-25d8-44a2-846c-1fcdc83dd412)
![img_2](https://github.com/user-attachments/assets/afb67344-1409-4343-80f4-4426fee8d1a1)
