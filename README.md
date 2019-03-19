网上关于springboot2+security+oauth2的东西很多  
但是看过后觉得普遍不接地气  
为什么这么说呢  
因为  

但凡正规点的公司，都是LDAP管理账户啊  
所以我写了springboot2+security+oauth2+ldap  

0319  
加上了sso的支持  
调用本auth方法：  
在开发的sso client端，@EnableOAuth2Sso并配置  
security.oauth2.client.clientId=client  
security.oauth2.client.clientSecret=client  
security.oauth2.client.accessTokenUri=http://localhost:8181/oauth/token  
security.oauth2.client.userAuthorizationUri=http://localhost:8181/oauth/authorize  
security.oauth2.resource.userInfoUri=http://localhost:8181/me  

