package action;

import com.opensymphony.xwork2.ActionSupport;
import entity.Message;
import remote.JNDIFactory;
import service.MessageService;

import java.util.List;


/**
 * Created by zzt on 2/20/16.
 * <p>
 * Usage:
 */
public class MessageAction extends ActionSupport {

    private MessageService messageService =
            (MessageService) JNDIFactory.getResource("ejb:/javaeeHomeworkEJB_exploded//MessageEJB!service.MessageService");
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String msgSend() throws Exception {
        // TODO: 2/26/16 change to list
        messageService.addMsg(msg, SessionManagement.getUid());
        return super.execute();
    }

    // for table list response
    private List<Message> records;
    private String result;
    private String message;

    private int totalRecordCount;
    // Holds Start Page Index
    private int jtStartIndex;
    // Hold records to be displayed per Page
    private int jtPageSize;

    public List<Message> getRecords() {
        return records;
    }

    public void setRecords(List<Message> records) {
        this.records = records;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public int getJtStartIndex() {
        return jtStartIndex;
    }

    public void setJtStartIndex(int jtStartIndex) {
        this.jtStartIndex = jtStartIndex;
    }

    public int getJtPageSize() {
        return jtPageSize;
    }

    public void setJtPageSize(int jtPageSize) {
        this.jtPageSize = jtPageSize;
    }

    public String msgList() throws Exception {
        try {
            assert messageService != null;
            int uid = SessionManagement.getUid();
            records = messageService.userMsg(uid, jtStartIndex, jtPageSize);
            totalRecordCount = messageService.countUserMsg(uid);
            result = JTableHelper.OK;
        } catch (Exception e) {
            e.printStackTrace();
            result = JTableHelper.ERROR;
            return ERROR;
        }
        return SUCCESS;
    }

    private int mid;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String msgDelete() throws Exception {
        try {
            messageService.deleteMsg(mid);
            result = JTableHelper.OK;
        } catch (Exception e) {
            e.printStackTrace();
            result = JTableHelper.ERROR;
            return ERROR;
        }
        return SUCCESS;
    }
}
