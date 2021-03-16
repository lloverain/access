package com.company.entity;

/**
 * @author 杨佳颖
 */
public class DB {
    private String ip;//ip
    private String port;//端口
    private String name;//数据库名
    private String user;//用户
    private String password;//密码

    @Override
    public String toString() {
        return "DB{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
