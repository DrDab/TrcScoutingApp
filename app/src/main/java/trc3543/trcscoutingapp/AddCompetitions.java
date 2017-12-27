package trc3543.trcscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressWarnings("all")
public class AddCompetitions extends AppCompatActivity
{
    /**
     *
     *  Copyright (c) 2017 _c0da_ (Victor Du)
     *
     *	Permission is hereby granted, free of charge, to any person obtaining a copy
     *	of this software and associated documentation files (the "Software"), to deal
     *	in the Software without restriction, including without limitation the rights
     *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     *	copies of the Software, and to permit persons to whom the Software is
     *	furnished to do so, subject to the following conditions:
     *
     *	The above copyright notice and this permission notice shall be included in all
     *	copies or substantial portions of the Software.
     *
     *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     *	SOFTWARE.
     */

    static ArrayAdapter<String> adapter;
    static ListView contestList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_competitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contestList = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter < String >
                (AddCompetitions.this, android.R.layout.simple_list_item_1,
                        DataStore.contests);

        contestList.setAdapter(adapter);
        contestList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView arg0, View arg1,int position, long arg3)
            {
                // position = position selected
                AlertDialog alertDialog = new AlertDialog.Builder(AddCompetitions.this).create();
                alertDialog.setTitle("Game Information");
                String s = DataStore.CsvFormattedContests.get(position);
                alertDialog.setMessage(s);
                alertDialog.show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // place a message
                Snackbar.make(view, "Enter competition information please", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                openCompNamePrompt();

            }
        });

        if (DataStore.contests.size() == 0)
        {
            addToList("No Entries Yet");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_competitions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void addToList(String s)
    {
        if (DataStore.contests.contains("No Entries Yet"))
        {
            removeFromList("No Entries Yet");
        }
        DataStore.contests.add(s);
        adapter.notifyDataSetChanged();
    }

    public static void removeFromList(String s)
    {
        DataStore.contests.remove(s);
        adapter.notifyDataSetChanged();
    }

    public void openCompNamePrompt()
    {
        Intent intent = new Intent(this, SetCompetitionName.class);
        startActivity(intent);
    }


}

