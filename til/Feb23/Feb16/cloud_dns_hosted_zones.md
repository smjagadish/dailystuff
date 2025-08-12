![image](https://user-images.githubusercontent.com/98840334/220382390-5876371f-087f-4235-9085-adba47f8fe34.png)

Description:

The concept of private hosted zones is more or less similar between AWS and Azure. The private hosted zone is a container for private DNS names and A/AAAA record mappings . Any entry in this is natively resolved by AWS (through the VPC local DNS resolver) and Azure (through the standard Azure DNS server) when the query is from within the network( Vnet or VPC) mapped to the hosted zone. The entries contained in the private hosted zone are not returned if the query doesn't come from within the mapped network . This means DNS queries for the private hosted zone info from the internet would potentially hit a blackhole

On-Prem integration

The setup is interesting in the case of on-prem integration . Here a dns resolver is created in the Vnet or VPC with an outbound and inbound endpoint. the outbound endpoint is used to forward on-prem DNS queries from AWS/Azure to on-prem DNS resolver through VPN/direct connect/private link. the inbound endpoint on the other hand is used to resove aws/azure DNS queries from on=prem . the resolver ruleset will determine if queries are fowarded to the outbound endpoint.
