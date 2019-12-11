package com.hthjsj.analysis.meta;

/**
 * <p> @Date : 2019/12/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ManualMetaObject extends MetaObject {

    @Override
    public MetaJudge metaType() {
        return new MetaJudge(MetaJudge.MANUAL);
    }
}
