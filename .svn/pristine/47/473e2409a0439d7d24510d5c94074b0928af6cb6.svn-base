package de.sloc.sspies.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import de.sloc.sspies.activesync.client.Autodiscover;
import de.sloc.sspies.activesync.client.ServerInfo;
import de.sloc.sspies.activesync.wbxml.codepages.CodepageLoader;

public class MainActivity extends Activity
{
	private TextView info;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		CodepageLoader.loadCodepages();

		setContentView(R.layout.main);

		final Button nextButton = (Button) findViewById(R.id.button1);
		final TextView emailAddress = (TextView) findViewById(R.id.emailAddress);
		final TextView password = (TextView) findViewById(R.id.password);
		info = (TextView) findViewById(R.id.infoText);

		nextButton.setOnClickListener(new OnClickListener()
		{

			public void onClick(View view)
			{

				new AutodiscoverTask().execute(emailAddress.getText().toString(), password.getText().toString());

			}
		});

		// int[] bla = getResources().getIntArray(R.array.bla);
		// int color =
		// getResources().obtainTypedArray(R.array.colors).getColor(1, 0);
		// TextView statusLog = (TextView) findViewById(R.id.statusLog);
		// statusLog.setText(Integer.toString(color));

		// TextView welcomeMessage = (TextView)
		// findViewById(R.id.welcome_message);
		// welcomeMessage.setText("blupp");
	}

	protected class AutodiscoverTask extends AsyncTask<String, Integer, ServerInfo>
	{
		protected ServerInfo doInBackground(String...params)
		{
			ServerInfo autodiscover = Autodiscover.discover(params[0], params[1]);

			return autodiscover;
		}

		protected void onPostExecute(ServerInfo result)
		{
			info.setText(result.getServerUrl());
		}
	}
}