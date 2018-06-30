package com.tunnel.common.bean;

import com.github.pagehelper.PageInfo;

public class PageRet extends BasicRet{

    public class PageInfoData{
        private PageInfo pageInfo;

        public PageInfo getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }
    }

    public   PageInfoData data = new PageInfoData();

    public PageInfoData getData() {
        return data;
    }

    public void setData(PageInfoData data) {
        this.data = data;
    }
}
