Native CPU Manager in K8s
*************************

base cpu pool : 16 logical cpus
reserved cpu : 0,1,2,3
initial starting pool : 4-16
kubelet  : outside of k8s responsibility to schedule them onto cpuset encompassing 0,1,2,3

eg: etc/systemd/system.conf -> CPUAffinity [ global config ] 

runc process : 0,1,2,3 [ handled by kubelet ]
system daemons/process such as udev, networkd : outside of k8s responsibility to schedule them onto cpuset encompassing 0,1,2,3

eg: similar to strategy with kubelet

pod sandbox process : can be aligned with 0,1,2,3 if infra_ctr option of runc is used sensibly
burstable and best effort pods : all cpus available in the pool

1st pod with req/limit for 2 cpus
excl pool #1 : 4,5
initial shared pool for burstable and best effort pods : 6-16 + 0,1,2,3

2nd pod with req/limit for 4 cpus
excl pool #2 : 6,7,8,9
initial shared pool for burstable and best effort pods- 10-16 + 0,1,2,3

