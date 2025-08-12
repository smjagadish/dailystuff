 Some notes on aws and azure wrt accounts , subscriptions and the whole shebang

AWS

* starts off with the concept of an 'organization'
* organization is the at the top of the hierarchy in aws account mgmt , think of it as the root entity
* any policies/rules defined at the organization level will apply for everything under it ( i.e. for the full hierarchy)
* the 'account' associated with the organization can be thought of as the administrator account
* only the administrator account can add sub-entities (OU's) and non-OU member accounts
* administrator account can delegate (i suppose) permissions to the OU's and contained member accounts
* sub-entities called as org units (OU's) are the next level of hierarchy
* the sub entities can then act as the placeholder for the member accounts (you can have member accounts directly under the root as well w/o being linked to any OU)
* the member accounts (as well as administrator account) can then create IAM users/roles
* billing is always at the org level in AWS

Azure

* starts off with the concept of a 'tenant'
* tenant is at the top of the hierarchy in azure account mgmt
* i presume each tenant is associated with an Azure AD instance
* tenants create subscriptions , which are the logical placeholders for all the resources ( through resource groups)
* billing is at the subscription level . this is a key diff compared to aws
* you can have as many tenants and each can have their own set of subscriptions
* tenants can always access any content within their hosted subscriptions
* policies at tenant level would cascade to the contained entities

NOTE : As I uncover more info , will update these notes/observations
