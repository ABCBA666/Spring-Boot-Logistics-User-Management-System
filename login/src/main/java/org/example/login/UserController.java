package org.example.login;

import javax.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/login")
    public Response loginUser(@RequestBody User user) {
        String uname = user.getUname();
        String pwd = user.getPwd();
        if (uname.equals("") || pwd.equals("")) {
            return new Response(100, "用户名或密码不能为空", "");
        } else {
            if (userMapper.login(uname, pwd) != null) {
                return new Response(200, "操作成功", "");
            } else {
                return new Response(500, "用户不存在/密码错误", "");
            }
        }
    }

    @PostMapping("/register")
    public Response register(@RequestBody User user) {
        String uname = user.getUname();
        String pwd = user.getPwd();
        if (uname.equals("") || pwd.equals("")) {
            return new Response(100, "用户名或密码不能为空", "");
        } else {
            if (userMapper.registerByName(user.getUname()) == null) {
                userMapper.register(user);
                return new Response(200, "操作成功", "");
            } else {
                return new Response(500, "注册失败", "用户已存在");
            }
        }
    }

    @GetMapping("/user/{uid}")
    public Response getUserInfo(@PathVariable Integer uid) {
        User user = userMapper.getUserById(uid);
        if (user != null) {
            return new Response(200, "操作成功", user.toString());
        } else {
            return new Response(500, "用户不存在", "");
        }
    }

    @PutMapping("/update/{uid}")
    public Response updateUserInfo(@PathVariable Integer uid, @RequestBody User user) {
        user.setUid(uid);
        int result = userMapper.updateUserInfo(user);
        if (result > 0) {
            return new Response(200, "操作成功", "");
        } else {
            return new Response(500, "更新失败", "");
        }
    }

    @PostMapping("/user/{uid}/role")
    public Response assignRoleToUser(@PathVariable Integer uid, @RequestBody RoleAssignmentRequest roleAssignmentRequest) {
        String roleName = roleAssignmentRequest.getRoleName();
        int result = userMapper.assignRole(uid, roleName);
        if (result > 0) {
            return new Response(200, "角色分配成功", "");
        } else {
            return new Response(500, "角色分配失败", "");
        }
    }

    @GetMapping("/user/{uid}/permissions")
    public Response getUserPermissions(@PathVariable Integer uid) {
        List<String> permissions = userMapper.getUserPermissions(uid);
        if (permissions != null) {
            return new Response(200, "操作成功", permissions.toString());
        } else {
            return new Response(500, "用户权限查询失败", "");
        }
    }

    @DeleteMapping("/user/{uid}")
    public Response deleteUser(@PathVariable Integer uid) {
        try {
            int result = userMapper.deleteUser(uid);
            if (result == 0) {
                System.out.println("没有数据被删除，可能是用户不存在或外键约束问题");
                return new Response(500, "删除失败，用户不存在或其他问题", "");
            }
            logOperation(uid, "删除用户", "删除了用户信息，uid=" + uid);
            return new Response(200, "操作成功", "");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(500, "删除失败，用户存在相关数据依赖", "");
        }
    }   //存在一些bug,需要进一步测试和调试。可以采用级联删除



    private void logOperation(Integer userId, String operationType, String details) {
        userMapper.insertOperationLog(userId, operationType, details);
    }

    @PutMapping("/updatelog/{uid}")
    public Response updateUserInfoWithLog(@PathVariable Integer uid, @RequestBody User user) {
        user.setUid(uid);
        int result = userMapper.updateUserInfo(user);
        logOperation(uid, "更新用户信息", "更新了用户信息，uid=" + uid);
        if (result > 0) {
            return new Response(200, "操作成功", "");
        } else {
            return new Response(500, "更新失败", "");
        }
    }
}
