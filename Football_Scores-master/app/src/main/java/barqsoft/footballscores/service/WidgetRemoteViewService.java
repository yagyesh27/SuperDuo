package barqsoft.footballscores.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.FootballWidget;

public class WidgetRemoteViewService extends RemoteViewsService {

    static {
        Log.d("RemoteViewsService", "static initializer ");
    }

    public WidgetRemoteViewService() {
        Log.d("WidgetRemoteViewService" , "WidgetRemoteViewService ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("on update", "onGetViewFactory ");
        MyRemoteViewsFactory dataProvider = new MyRemoteViewsFactory(getApplicationContext(), intent);
        return dataProvider;

        //return new MyRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}


class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    private int mAppWidgetId;
    ArrayList mCollections = new ArrayList();

    static {

        Log.d("RemoteViewsFactory", "static initializer ");

    }


    public MyRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        Log.d("on update", "MyRemoteViewsFactory ");
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {
        Log.d("oncreate","oncreate");
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("on update", "getViewAt ");
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),android.R.layout.simple_list_item_1);
        mView.setTextViewText(android.R.id.text1, mCollections.get(position).toString());
        mView.setTextColor(android.R.id.text1, Color.BLACK);
        return mView;
        //return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }



    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void initData() {
        Log.d("on update", "initData ");
        mCollections.clear();
        for (int i = 1; i <= 10; i++) {
            mCollections.add("ListView item " + i);
        }
    }
}