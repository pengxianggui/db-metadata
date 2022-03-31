# 创建元对象
## 为员工表创建元对象
为员工表创建元对象: `t_employee`:
![创建元对象](/demo/img.png)

## 为部门表创建元对象
为部门表创建元对象: `t_department`
![](/demo/img_1.png)

:::tip
元对象编码没什么实际含义, 唯一、好记、方便和表关联起来即可。因此建议, 就直接用表名(除非多库时，表名冲突）。
:::

## 编辑元对象
编辑元对象`t_department`, 设置其`数据结构`为`树结构`:
![](/demo/img_2.png)
:::tip
**强烈建议元对象和元字段的配置先完成**, 然后再生成实例配置。因为当实例配置生成后, 再对元对象和元字段的配置会触发DbMeta智能推导并更新已生成的实例配置, 可能会
覆盖用户编辑的实例配置。导致你还得微调下实例配置。所幸DbMeta重新推导时，不会干预太多。
:::
