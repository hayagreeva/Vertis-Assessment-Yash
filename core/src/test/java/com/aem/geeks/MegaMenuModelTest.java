package com.aem.geeks;

import com.aem.geeks.core.models.MegaMenuHeaderModel;
import com.aem.geeks.core.pojos.MegaMenuParentPagePojo;
import com.aemvertis.core.testcontext.VertisAemContext;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class MegaMenuModelTest {

    private final AemContext aemContext = VertisAemContext.newAemContext();

    private MegaMenuHeaderModel megaMenuModel;

    private Page currentPage;

    private Resource resource;

    public static final String HOME_PAGE_PATH = "/content/aemgeeks/us/en";
    public static final String HOME_PAGE_JSON = "/content/aemgeeks/home.json";
    public static final String SLASH = "/";
    public static final String JCR_CONTENT = "jcr:content";
    public static final String MEGAMENU_COMP = "/root/container/megamenu";
    public static final int SIX = 6;

    @BeforeEach
    public void setup() {
        aemContext.addModelsForClasses(MegaMenuHeaderModel.class);
        aemContext.load().json(HOME_PAGE_JSON, HOME_PAGE_PATH);
        currentPage = aemContext.currentPage(HOME_PAGE_PATH);
        aemContext.resourceResolver().getResource(HOME_PAGE_PATH);
        aemContext.currentResource(HOME_PAGE_PATH + SLASH + JCR_CONTENT + MEGAMENU_COMP);
        resource = aemContext.request().getResource();
    }

    @Test
    void testInit() {
        megaMenuModel = resource.adaptTo(MegaMenuHeaderModel.class);
        List<MegaMenuParentPagePojo> megaMenuPojoList = megaMenuModel.getParentPages();
        assertEquals(SIX, megaMenuPojoList.size());
    }
}