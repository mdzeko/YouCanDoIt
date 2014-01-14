package foi.youcandoit;

import foi.youcandoit.AktivnostPlaniraj;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

public class MySupportMapFragment extends SupportMapFragment {
	private View originalView;
	private TouchableLayout myView;
	private AktivnostPlaniraj parent;
	
	public void setParent(AktivnostPlaniraj parent)
	{
		this.parent = parent;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		originalView = super.onCreateView(inflater, container, savedInstanceState);
		myView = new TouchableLayout(getActivity());
		myView.addView(originalView);
		
		return myView;
	}
	
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return originalView;
	}
	
	private class TouchableLayout extends FrameLayout
	{
		public TouchableLayout(Context context)
		{
			super(context);
		}
		
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			if(parent != null) parent.onMyTouchEvent(ev);
			return super.dispatchTouchEvent(ev);
		}
	}
}

