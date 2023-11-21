# b2cStore
## 包含前后端服务。
### 前端
前端服务需要按照 nodejs，安装对应的环境，使用 npm run serve 指令即可运行
### 后端
后端使用 nacos，mysql，redis，rebbitmq，spring-cache，feign需要在项目中配置对应的注册文件。<\br>
分别跑上对应的服务即可.<\br>
需要注意的是服务之间存在依赖，所以需要注意不同服务的启动顺序，建议启动顺序为 nacos->gateway->static->user|category|carousel->product->collect|cart->order->admin
