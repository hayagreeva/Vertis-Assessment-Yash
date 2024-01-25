package com.aem.geeks.core.models;


import com.aem.geeks.core.pojos.MegaMenuParentPagePojo;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MegaMenuHeaderModel {

    @ValueMapValue
    private String megaMenuRootPath;
    @SlingObject
    private ResourceResolver resolver;
    private List<MegaMenuParentPagePojo> parentPages;
    private static final int PAGE_LEVEL = 3;
    private static final String HIDE_IN_NAV = "hideInNav";
    private static final String TRUE = "true";
    private static final String HIDE_IN_NAV_FOR_SUBPAGE = "hideAllSubpagesInNavigation";
    private static final Logger LOG = LoggerFactory.getLogger(MegaMenuHeaderModel.class);

    @PostConstruct
    protected void init() {
        parentPages = new ArrayList<>();
        Resource resource = resolver.getResource(megaMenuRootPath);
        if (resource != null) {
            Page page = resource.adaptTo(Page.class);
            parentPages = getAllChildPages(page, 1);
        }
    }

    List<MegaMenuParentPagePojo> getAllChildPages(Page page, int level) {
        List<MegaMenuParentPagePojo> childPagesLists = new ArrayList<>();
        if (PAGE_LEVEL < level) {
            return childPagesLists;
        }
        Iterator<Page> resourceIterator = page.listChildren();
        while (resourceIterator.hasNext()) {
            MegaMenuParentPagePojo megaMenuParentPagePojo = new MegaMenuParentPagePojo();
            Page childPage = resourceIterator.next();
            ValueMap valueMap = childPage.getProperties();
            if (!(valueMap.containsKey(HIDE_IN_NAV) && valueMap.get(HIDE_IN_NAV, String.class).equals(TRUE))) {
                megaMenuParentPagePojo.setTitle(childPage.getTitle());
                megaMenuParentPagePojo.setPath(childPage.getPath());
                ValueMap childValueMap = childPage.getProperties();
                if (valueMap.containsKey(HIDE_IN_NAV_FOR_SUBPAGE) && childValueMap.get(HIDE_IN_NAV_FOR_SUBPAGE, String.class).equals("true")) {
                    LOG.info("Hide all subpages in navigation");
                } else {
                    List<MegaMenuParentPagePojo> childList = getAllChildPages(childPage, level + 1);
                    megaMenuParentPagePojo.setChildPageList(childList);
                }
                childPagesLists.add(megaMenuParentPagePojo);
            } else {
                LOG.info("Hide page in navigation");
            }
        }
        return childPagesLists;
    }

    public List<MegaMenuParentPagePojo> getParentPages() {
        return parentPages;
    }
}
