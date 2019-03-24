package com.pwang.wanandroid.common;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * <pre>
 *    @author :WangPan
 *    @e-mail :501098462@qq.con
 *    @time   :2019/03/24
 *    @desc   :
 *    @version:1.0
 * </pre>
 */
public final class RxBus {

    private final FlowableProcessor <Object> mFlowable;

    /**
     *  私有构造方法，防止被外部类实例化
     */
    private RxBus() {
        mFlowable = PublishProcessor.create().toSerialized();
    }

    /**
     * 获取单利实例
     * @return
     */
    public static RxBus getInstance() {
        return RxHolder.INSTANCE;
    }

    /**静态内部类实现单例模式*/
    private static class RxHolder{
        private static final RxBus INSTANCE = new RxBus();
    }

    /**
     * 发布一个新的事件
     * @param obj 事件类型
     */
    public void post(Object obj){
        mFlowable.onNext(obj);
    }

    /**
     * 根据事件类型 type 获取特定订阅事件的被观察者进行数据处理
     * @param type 事件
     * @param <T> 事件类型
     * @return Flowable 用于获取订阅的事件
     */
    public <T> Flowable<T> toFlowable(Class<T> type){
        return mFlowable.ofType(type);
    }
}
