# 环境准备

## 操作系统

系统推荐使用`x86`架构`rockylinux 8.9`、`Anolis OS 8.9`、`openEuler 22.03`

**文件系统**

`ext4` 和 `xfs` 文件系统均支持。

## Java 版本

请使用 `JDK17` 或者 `JDK21`，推荐版本：`jdk-17.0.13_linux-x64_bin.tar.gz`。

## 数据库选型

支持`mysql/mariadb/postgresql`和达梦。推荐`mysql 8.0`和`postgresql16.4`。

##  打包`bigtop-manager`

```shell
cd path/to/bigtop-manage
tar -zcvf bigtop-manager.tgz --strip-components=1 -C `echo $PWD/bigtop-manager-server/target` bigtop-manager-server -C `echo $PWD/bigtop-manager-agent/target` bigtop-manager-agent
```

## 检测和关闭系统防火墙

如果发现端口不通，可以试着关闭防火墙，确认是否是本机防火墙造成。如果是防火墙造成，可以根据配置的 Doris 各组件端口打开相应的端口通信。

```sql
sudo systemctl stop firewalld.service
sudo systemctl disable firewalld.service
```

## 配置时间同步服务

所有集群机器要进行时钟同步，避免因为时钟问题引发的元数据不一致导致服务出现异常。

通常情况下，可以通过配置 `NTP/CHRONY` 服务保证各节点时钟同步。

```shell
sudo systemctl start ntpd.service
sudo systemctl enable ntpd.service

sudo systemctl start chronyd.service
sudo systemctl enable chronyd.service
```

## 配置ssh免密

生成ssh秘钥后分发秘钥。

```shell
ssh-keygen -N '' -t rsa -b 2048 -f /etc/ssh/ssh_host_rsa_key
ssh-keygen -N '' -t ecdsa -b 256 -f /etc/ssh/ssh_host_ecdsa_key
ssh-keygen -N '' -t ed25519 -b 256 -f /etc/ssh/ssh_host_ed25519_key
```

## 配置`Hosts`

```shell
hostnamectl set-hostname your-host-name
 
vim /etc/hosts
10.10.0.101 bm1
10.10.0.102 bm2
10.10.0.103 bm3
```

