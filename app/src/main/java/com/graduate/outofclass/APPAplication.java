package com.graduate.outofclass;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.graduate.outofclass.utils.GetToast;
import com.tencent.smtt.sdk.QbSdk;

public class APPAplication extends Application {

	// 在 application 里面加入
	@Override
	public void onCreate() {
		super.onCreate();

		preinitX5WebCore();
		//预加载x5内核
		Intent intent = new Intent(this, AdvanceLoadX5Service.class);
		startService(intent);
	}

	private void preinitX5WebCore() {
		if (!QbSdk.isTbsCoreInited()) {
			QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
		}
	}


	// x5 init service
	public class AdvanceLoadX5Service extends Service {
		@Nullable
		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onCreate() {
			super.onCreate();
			initX5();
		}

		private void initX5() {
			//  预加载X5内核
			QbSdk.initX5Environment(getApplicationContext(), cb);
		}

		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//初始化完成回调
				if(!arg0) GetToast.useString(getApplicationContext(),"x5调用失败");
			}

			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
			}
		};

	}

}
