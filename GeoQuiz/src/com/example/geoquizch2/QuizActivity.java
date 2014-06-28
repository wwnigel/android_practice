package com.example.geoquizch2;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {
	// Private variable and functions
	private static final String TAG = "QuizActivity";
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private TextView mQuestionTextView;
	private int mCurrentIndex = 0;
	private boolean mIsCheater;
	private static final String KEY_INDEX = "index";
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_turkey, false),
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true),
	};
	
	
	private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
	}
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		
		if (mIsCheater) {
			messageResId = R.string.judgment_toast;
		} else {
			messageResId = (answerIsTrue==userPressedTrue) ? R.string.correct_toast : R.string.incorrect_toast;
		}
		
		Toast.makeText(this,  messageResId, Toast.LENGTH_SHORT).show();
	}
	
	
	// Protected activity functions
	@TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setSubtitle("Bodies of Water");
        	
        }
        
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			//@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
				updateQuestion();
			}
		});
        
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
			//@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
        
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
        
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent i = new Intent(QuizActivity.this, CheatActivity.class);
        		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        		i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        		// startActivity(i);
        		startActivityForResult(i,0);
        		
        	}
        });
        
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex+5) % mQuestionBank.length;
				updateQuestion();
			}
		});
        
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Updating question text for question #" + mCurrentIndex);
				mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});
        
        if (savedInstanceState != null) {
        	mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();
        //Log.d(TAG, "GeoQuiz with multiple questions is luanched!");
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (data == null) {
    		return;
    	}
    	mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    		
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
    	super.onSaveInstanceState(savedInstanceState);
    	Log.i(TAG, "onSaveInstantState");
    	savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onStart() {
	    super.onStart();
	    Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
	    super.onPause();
	    Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
	    super.onResume();
	    Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
	    super.onStop();
	    Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
	    super.onDestroy();
	    Log.d(TAG, "onDestroy() called");
    }


}
