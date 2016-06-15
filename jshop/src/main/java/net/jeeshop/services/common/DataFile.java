package net.jeeshop.services.common;import java.io.Serializable;import net.jeeshop.core.dao.QueryModel;/** * 资料中心-文件 * @author W *  */public class DataFile extends QueryModel implements Serializable {	private static final long serialVersionUID = 1L;	private String id;	private String cId;//数据中心ID	private String fname;//文件名	private String fsize;//文件大小	private String furl;//文件地址	private String fbigurl;//大文件地址	private String createDate;	private String userId;//用户ID	private int order2;	private String playurl;		public void clear() {		super.clear();		id = null;		cId = null;		fname = null;		fsize = null;		furl = null;		fbigurl = null;		userId = null;		order2=0;		playurl=null;	}		private String[] fnames ;	private String[] furls ;	private String[] fbigurls ;	private String[] fsizes;	private String[] ids;	private String[] playurls;	public String[] getFnames() {		return fnames;	}	public void setFnames(String[] fnames) {		this.fnames = fnames;	}	public String[] getFurls() {		return furls;	}	public void setFurls(String[] furls) {		this.furls = furls;	}	public String[] getFbigurls() {		return fbigurls;	}	public void setFbigurls(String[] fbigurls) {		this.fbigurls = fbigurls;	}	public String[] getFsizes() {		return fsizes;	}	public void setFsizes(String[] fsizes) {		this.fsizes = fsizes;	}	public String getId() {		return id;	}	public void setId(String id) {		this.id = id;	}	public String getcId() {		return cId;	}	public void setcId(String cId) {		this.cId = cId;	}	public String getFname() {		return fname;	}	public void setFname(String fname) {		this.fname = fname;	}	public String getFsize() {		return fsize;	}	public void setFsize(String fsize) {		this.fsize = fsize;	}	public String getFurl() {		return furl;	}	public void setFurl(String furl) {		this.furl = furl;	}	public String getFbigurl() {		return fbigurl;	}	public void setFbigurl(String fbigurl) {		this.fbigurl = fbigurl;	}	public String getCreateDate() {		return createDate;	}	public void setCreateDate(String createDate) {		this.createDate = createDate;	}	public String getUserId() {		return userId;	}	public void setUserId(String userId) {		this.userId = userId;	}	public int getOrder2() {		return order2;	}	public void setOrder2(int order2) {		this.order2 = order2;	}	public String[] getIds() {		return ids;	}	public void setIds(String[] ids) {		this.ids = ids;	}	public String getPlayurl() {		return playurl;	}	public void setPlayurl(String playurl) {		this.playurl = playurl;	}	public String[] getPlayurls() {		return playurls;	}	public void setPlayurls(String[] playurls) {		this.playurls = playurls;	}	}