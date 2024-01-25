package com.aem.geeks.core.pojos;

import java.util.List;
public class MegaMenuParentPagePojo {
    private String title;

    private String path;
    private List<MegaMenuParentPagePojo> chilPageList;

    public void setTitle(String titles) {
        this.title = titles;
    }

    public void setPath(String paths) {
        this.path = paths;
    }
    public void setChildPageList(List<MegaMenuParentPagePojo> chilPageLists) {
        this.chilPageList = chilPageLists;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }
    public List<MegaMenuParentPagePojo> getChilPageList() {
        return chilPageList;
    }
}
