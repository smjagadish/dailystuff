local ssh server based on openssh

docker run -d -p 2222:2222 --rm -it -e USER_PASSWORD=linux -e SUDO_ACCESS=true -e PASSWORD_ACCESS=true -e USER_NAME=linux -e PUBLIC_KEY="ecdsa-sha2-nistp521 AAAAE2VjZHNhLXNoYTItbmlzdHA1MjEAAAAIbmlzdHA1MjEAAACFBAFSRruupF5j4EsW57qpS0zmYy+nxmFUMxgx1Wtf5SmFMw/lIkK1ANYGK23D/ze2rrcrmKKekIrBZOb6MygX4pOM0gElm5C2izmFfI84g0RkB7nBt5qw64eeLwGQQFDd8IYbxlNxfbJsvf+Iz7bFJ6LCKd9PeUWZnm+GqMOFnPhzfyV/3A== root@321bc0e0879f" linuxserver/openssh-server

* remover password_access if restricting to pure key based login 
* need to enhance this to support ssh login based on certificates 
