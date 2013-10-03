package game.TBSG;

import game.BO.unit.Unit;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Window;
import android.view.WindowManager;

public class turnBasedStrategyGame extends Activity implements OnGameInteractionListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        setContentView(R.layout.main);
        registerForContextMenu(findViewById(R.id.mainView));
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	// TODO Auto-generated method stub
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    
    public void onUnitSelected(Unit unit) {
    	openContextMenu(findViewById(R.id.mainView));
    }
}