package com.wgw.flux

/**
 * 保存动作类型以及携带数据
 *
 * @author Created by 汪高皖 on 2019/2/22 0021 16:27
 */
class Action private constructor(
    /**
     * 动作所要分发到的目标类型。当[target]值为null时，通过此值来分发动作到对应[Store]
     */
    var type: String,
    var data: Any? = null,
    var throwable: Throwable? = null,
    /**
     * 动作所要分发到的目标[Store]。
     * 如果该值不为空，则该动作只会分发到对应的[Store],
     * 如果没有[Store]注册到[Dispatcher]，则该动作流逝
     */
    var target: Store? = null
) : ObjectPool.Poolable() {

    override fun toString() =
        "Action(type='$type', store=$target, throwable=$throwable, data=$data)"

    override fun instantiate(): ObjectPool.Poolable = Action(EMPTY_TYPE)

    override fun recycle() {
        type = EMPTY_TYPE
        throwable = null
        data = null
        target = null
    }

    companion object {
        /**
         * 空(无效的)ActionType
         */
        const val EMPTY_TYPE = "emptyType"

        /**
         * 初始池大小，只能在未调用[getInstance]、
         * [recycleInstance]、[recycleInstances]方法之前设置
         */
        var INIT_POOL_SIZE = 20

        /**
         * 当池容量不够时池每次增长比例，只能在未调用[getInstance]、
         * [recycleInstance]、[recycleInstances]方法之前设置
         */
        var REPLENISH_PERCENTAGE = 0.5f

        /**
         * 获取实例，无需使用请调用[recycleInstance]或[recycleInstances]
         */
        @JvmStatic
        fun getInstance(type: String): Action {
            val result = Inner.pool.get()
            result.type = type
            return result
        }

        /**
         * 回收[Action]
         */
        @JvmStatic
        fun recycleInstance(instance: Action) {
            Inner.pool.recycle(instance)
        }

        /**
         * 回收[Action]集合
         */
        @JvmStatic
        fun recycleInstances(instances: List<Action>) {
            Inner.pool.recycle(instances)
        }

        /**
         * 延迟初始化
         */
        private object Inner {
            @Suppress("JoinDeclarationAndAssignment")
            var pool: ObjectPool<Action>

            init {
                pool = ObjectPool.create(INIT_POOL_SIZE, Action(EMPTY_TYPE))
                pool.replenishPercentage = REPLENISH_PERCENTAGE
            }
        }
    }
}
