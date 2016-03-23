package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.service.WidgetRemoteViewService;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link FootballWidgetConfigureActivity FootballWidgetConfigureActivity}
 */
public class FootballWidget extends AppWidgetProvider {

    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 8;
    public static final int COL_MATCHTIME = 2;
    public double detail_match_id = 0;
    private String FOOTBALL_SCORES_HASHTAG = "#Football_Scores";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for ( int widgetid : appWidgetIds) {
            Log.d("on update", "onUpdate ");
            //updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
            //RemoteViews mView = initViews(context, appWidgetManager, widgetid);
            Intent intent = new Intent(context, WidgetRemoteViewService.class);
            //Log.d("mviewU",mView.toString());
            //Log.d("mviewU",mView.);
            //mView.setTextViewText();
            RemoteViews mView = new RemoteViews(context.getPackageName(), R.layout.football_widget);
            try{
                mView.setTextViewText(R.id.away_name1,"away");
            }catch (Exception e){
                Log.e("exc",e.toString());
            }
            try {
                //mView.setRemoteAdapter(R.id.scores_list, intent);
            }catch (Exception e){
                Log.e("exc",e.toString());
            }

            //ScoresProvider scoresProvider = new ScoresProvider();

            Date fragmentdate = new Date(System.currentTimeMillis());//-(86400000));
            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
            String currDate = mformat.format(fragmentdate).toString();
            Log.d("widget", mformat.format(fragmentdate).toString());
            String arr[] = {currDate};

            Cursor cursor = context.getContentResolver().query(DatabaseContract.BASE_CONTENT_URI,null,null,null,null);


            if(/*(cursor.moveToFirst())*/ true){

                while (cursor.moveToNext() ) {


                    /*Log.d("cr", cursor.getString(COL_HOME)+"12345");
                    Log.d("cr", cursor.getString(COL_DATE));
                    Log.d("cr", cursor.getString(COL_AWAY));
                    Log.d("cr", cursor.getInt(COL_HOME_GOALS)+"");
*/
                    Log.d("comp",cursor.getString(COL_DATE).compareTo(currDate)+"");
                    if ((cursor.getString(COL_DATE)).compareTo(currDate) == 0) {

                        break;

                    }



                }

                //Log.d("cr", cursor.getString(COL_HOME)+"rsxcdfytfuhvkg");


                try {
                    mView.setTextViewText(R.id.home_name1, cursor.getString(COL_HOME));
                    mView.setTextViewText(R.id.away_name1, cursor.getString(COL_AWAY));
                    mView.setTextViewText(R.id.data_textview1, cursor.getString(COL_MATCHTIME));
                    mView.setTextViewText(R.id.score_textview1, Utilies.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
                    double matchid = cursor.getDouble(COL_ID);
                    mView.setImageViewResource(R.id.home_crest1, Utilies.getTeamCrestByTeamName(cursor.getString(COL_HOME)));
                    mView.setImageViewResource(R.id.away_crest1, Utilies.getTeamCrestByTeamName(cursor.getString(COL_AWAY)));

                    mView.setContentDescription(R.id.home_name1, cursor.getString(COL_HOME));
                    mView.setContentDescription(R.id.away_name1, cursor.getString(COL_AWAY));
                    mView.setContentDescription(R.id.data_textview1, cursor.getString(COL_MATCHTIME));
                    mView.setContentDescription(R.id.score_textview1, Utilies.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
                    mView.setContentDescription(R.id.home_crest1, cursor.getString(COL_HOME));
                    mView.setContentDescription(R.id.away_crest1, cursor.getString(COL_AWAY));
                }catch (Exception e){
                    Log.e("widget set data",e.toString());
                }

                cursor.moveToFirst();
                cursor.close();
            }




            appWidgetManager.updateAppWidget(widgetid, mView);

        }
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            FootballWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private RemoteViews initViews(Context context,AppWidgetManager widgetManager, int widgetId) {
        Log.d("on initViews", "initViews ");
        RemoteViews mView = new RemoteViews(context.getPackageName(), R.layout.football_widget);

        Intent intent = new Intent(context, WidgetRemoteViewService.class);
        /*intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));*/
       // mView.setRemoteViewsAdapter(intent);
        Log.d("mviewI",mView.toString());
        Log.d("on initViews", "initViews1 ");
        return mView;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d("on updateAppWidget", "updateAppWidget ");
        CharSequence widgetText = FootballWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.football_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);


        // Instruct the widget manager to update the widget
        //appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

