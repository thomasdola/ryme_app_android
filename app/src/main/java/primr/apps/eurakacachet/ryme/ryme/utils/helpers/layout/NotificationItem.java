package primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;

import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.notification.BaseNotificationItem;
import com.liulishuo.filedownloader.util.FileDownloadHelper;

import primr.apps.eurakacachet.ryme.ryme.R;


public class NotificationItem extends BaseNotificationItem{

    public NotificationItem(int id, String title, String desc) {
        super(id, title, desc);
    }

    @Override
    public void show(boolean statusChanged, int status, boolean isShowProgress) {
        NotificationCompat.Builder builder = new NotificationCompat.
                Builder(FileDownloadHelper.getAppContext());

        String desc = getDesc();
        switch (status) {
            case FileDownloadStatus.pending:
                desc += " pending";
                break;
            case FileDownloadStatus.progress:
                desc += " progress";
                break;
            case FileDownloadStatus.retry:
                desc += " retry";
                break;
            case FileDownloadStatus.error:
                desc += " error";
                break;
            case FileDownloadStatus.paused:
                desc += " paused";
                break;
            case FileDownloadStatus.completed:
                desc += " completed";
                break;
            case FileDownloadStatus.warn:
                desc += " warn";
                break;
        }

        builder.setDefaults(Notification.DEFAULT_LIGHTS)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentTitle(getTitle())
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        if (statusChanged) {
            builder.setTicker(desc);
        }

        builder.setProgress(getTotal(), getSofar(), !isShowProgress);
        getManager().notify(getId(), builder.build());
    }

}
