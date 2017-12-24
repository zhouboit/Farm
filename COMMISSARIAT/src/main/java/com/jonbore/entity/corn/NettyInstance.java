package com.jonbore.entity.corn;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Jonbo
 * Date: 2017-12-24
 * Time: 21:33
 */
public class NettyInstance implements java.io.Serializable {

    private String ip;
    private int port;
    //1 online 0 offline
    private String status;
    //init time
    private Long initTime;
    //last heartbeat time
    private Long lastHeartBeatTime;
    //test heartbeat interval time
    private Long intervalHeartBeat;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getInitTime() {
        return initTime;
    }

    public void setInitTime(Long initTime) {
        this.initTime = initTime;
    }

    public Long getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(Long lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public Long getIntervalHeartBeat() {
        return intervalHeartBeat;
    }

    public void setIntervalHeartBeat(Long intervalHeartBeat) {
        this.intervalHeartBeat = intervalHeartBeat;
    }
}
