FragmentAnnotation
==================

Simple annotation for Android fragment building



```Java

public class MyFragment extends Fragment {

  @FragArg
    String name;
    
  @FragArg
  int value;
  
  @Override
  public void onCreate(Bundle savedInstanceState) { 
    super.onCreate(savedInstanceState);
    MyFragmentBuilder.inject(this); // here the values are read from the Bundle in `.getArguments`
  }

}

// when initing the fragment
MyFragment f = new MyFragmentBuilder
    .setName("name")
    .setValue(42).build; // the values are stored in a Bundle in the .`setArguments`




```
