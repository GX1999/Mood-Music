package gx.upc.edu.gittest;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment2 extends Fragment {


    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);
        Button button1 = (Button) getActivity().findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getActivity(),MusicService.class);
                intent1.putExtra("name","happy/");
                getActivity().startService(intent1);

                Intent intent=new Intent(getActivity(),ServiceActivity.class);
                startActivity(intent);

            }
        });


        Button button2 = (Button) getActivity().findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getActivity(),MusicService.class);
                intent1.putExtra("name","sad/");
                getActivity().startService(intent1);

                Intent intent=new Intent(getActivity(),ServiceActivity.class);
                startActivity(intent);

            }
        });

        Button button3 = (Button) getActivity().findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getActivity(),MusicService.class);
                intent1.putExtra("name","angry/");
                getActivity().startService(intent1);

                Intent intent=new Intent(getActivity(),ServiceActivity.class);
                startActivity(intent);

            }
        });


        Button button4 = (Button) getActivity().findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getActivity(),MusicService.class);
                intent1.putExtra("name","fear/");
                getActivity().startService(intent1);

                Intent intent=new Intent(getActivity(),ServiceActivity.class);
                startActivity(intent);

            }
        });


        Button button5 = (Button) getActivity().findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getActivity(),MusicService.class);
                intent1.putExtra("name","lone/");
                getActivity().startService(intent1);

                Intent intent=new Intent(getActivity(),ServiceActivity.class);
                startActivity(intent);

            }
        });


        Button button6 = (Button) getActivity().findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getActivity(),MusicService.class);
                intent1.putExtra("name","neutral/");
                getActivity().startService(intent1);

                Intent intent=new Intent(getActivity(),ServiceActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment2, container, false);
    }

}
