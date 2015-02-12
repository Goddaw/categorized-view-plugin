package org.jenkinsci.plugins.categorizedview;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.TopLevelItem;
import hudson.plugins.view.dashboard.DashboardPortlet;
import hudson.util.DescribableList;
import hudson.util.FormValidation;

public class CategorizedJobsPortlet extends DashboardPortlet {

	private final CategorizedJobsView view;
	
	@DataBoundConstructor
    public CategorizedJobsPortlet(String name) {
        super(name);
        view = new CategorizedJobsView(name);
    }
	
	public List<TopLevelItem> getGroupedItems() {
		return view.getGroupedItems();
	}

	public void migrateOldFormat() {
		view.migrateOldFormat();		
	}

	public void forcefullyDisableRecurseBecauseItCausesClassCastExceptionOnJenkins1_532_1(
			StaplerRequest req) {
		view.forcefullyDisableRecurseBecauseItCausesClassCastExceptionOnJenkins1_532_1(req);
	}
    
    public DescribableList<CategorizationCriteria, Descriptor<CategorizationCriteria>> getCategorizationCriteria() {
    	return view.getCategorizationCriteria();
	}
    
    public String getGroupClassFor(TopLevelItem item) {
    	return view.getGroupClassFor(item);
    }
    
    public boolean hasLink(TopLevelItem item) {
    	return item.getShortUrl() != null;
    }
    
    public boolean isGroupTopLevelItem(TopLevelItem item) {
    	return item instanceof GroupTopLevelItem;
    }
    
    @Extension
    public static class DashboardPortletDescriptor extends Descriptor<DashboardPortlet> {
    	
    	final CategorizedJobsView.DescriptorImpl descriptor = new CategorizedJobsView.DescriptorImpl();
    	
        @Override
        public String getDisplayName() {
        	return descriptor.getDisplayName();
        }
        
		public FormValidation doCheckIncludeRegex(@QueryParameter String value) 
				throws IOException, ServletException, InterruptedException {
			return descriptor.doCheckIncludeRegex(value);
		}
    }
};