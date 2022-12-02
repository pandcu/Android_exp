# 实验3-界面组件
## 1. ListView用法
新建一个Activity，在其布局文件内放置一个ListView,将其id设置为“animal”
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/animal"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:choiceMode="singleChoice" />

</LinearLayout>
```
![listview](/3_componentTest/screenshot/listview2.jpg)
新建一个xml布局文件，作为ListView中每行的布局，将文本框和图像框的id命名为“animalName”和“animalImage”，以便后续改变其所含文本和图片
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="70dp"
    android:background="@drawable/item_">

    <TextView
        android:id="@+id/animalName"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:gravity="center_vertical"
        android:paddingLeft="20dp"
        android:text="TextView"
        android:textColor="@color/black"
        android:textSize="16sp" />

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_weight="1">

        <ImageView
            android:id="@+id/animalImage"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_marginEnd="20dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </androidx.constraintlayout.widget.ConstraintLayout>
</LinearLayout>
```
![listview](/3_componentTest/screenshot/listview3.jpg)
在Activity中定义一个字符串数组和一个int型数组，分别用于存放每行的文字信息和图片信息
```java
private String[] animalname = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
private int[] animalimage = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey, R.drawable.dog, R.drawable.cat, R.drawable.elephant};
```
SimpleAdapter的构造函数如下
```java
SimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
```
> context：上下文
> data：数据源，一个包含Map的数据集合
>resource：item的布局文件
>from：数据来源的key的数组，与data的Map里的key对应
>to:  from中的每一项数据源 对应到的 item布局中对应组件的 id。

其中的data参数需要我们定义一由map映射构成的列表，在Activity中加入以下代码
```java
List<Map<String, Object>> listItems = new ArrayList<>();
    for(int i = 0; i < animalname.length; i++){
        Map<String, Object> listItem = new HashMap<String, Object>();
        listItem.put("text", animalname[i]);
        listItem.put("image", animalimage[i]);
        listItems.add(listItem);
    }
```
将对应参数传入构造函数，实例化一个SimpleAdapter对象，用id获取布局文件中的ListView，使用实例化出的SimpleAdapter为其构建内容
```java
SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.listview_item, new String[]{"text", "image"}, new int[]{R.id.animalName, R.id.animalImage});
ListView list = findViewById(R.id.animal);
list.setAdapter(simpleAdapter);
```
为ListView设定单击item事件，获取所点击的item中的文本内容，用toast的形式将其显示出来
```java
list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = view.findViewById(R.id.animalName);
        Toast toast = Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_SHORT);
        toast.show();
    }
});
```
为了实现item在被点击时变成红色的效果，在drawable文件夹下新建一个selector资源文件，在list_item的布局文件中设置背景颜色为引用该selector文件
```java
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@color/design_default_color_error" android:state_pressed="true"></item>
    <item android:drawable="@color/white"></item>
</selector>
```
最终实现效果如图  
![listview](/3_componentTest/screenshot/listview1.jpg)
## 2. 自定义布局的AlertDialog
新建一个xml布局文件作为AlertDialog的布局
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <TextView
        android:id="@+id/textView"
        android:layout_width="match_parent"
        android:layout_height="70dp"
        android:background="#FFC107"
        android:gravity="center"
        android:text="ANDROID APP"
        android:textColor="@color/cardview_light_background"
        android:textSize="34sp"
        android:textStyle="bold" />
    <EditText
        android:id="@+id/editTextTextPersonName"
        android:layout_width="match_parent"
        android:layout_height="70dp"
        android:ems="10"
        android:inputType="textPersonName"
        android:text="Username"
        android:textColor="@android:color/system_neutral1_400" />
    <EditText
        android:id="@+id/editTextTextPersonName2"
        android:layout_width="match_parent"
        android:layout_height="70dp"
        android:ems="10"
        android:inputType="textPersonName"
        android:text="Password"
        android:textColor="@android:color/system_neutral1_400" />
    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="#66515151"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="70dp"
        android:orientation="horizontal">
        <Button
            android:id="@+id/cancelbtn"
            android:layout_width="wrap_content"
            android:layout_height="70dp"
            android:layout_weight="1"
            android:background="@color/cardview_shadow_end_color"
            android:text="Cancel"
            android:textAllCaps="false" />
        <View
            android:layout_width="1dp"
            android:layout_height="match_parent"
            android:background="#66505050"/>
        <Button
            android:id="@+id/signinbtn"
            android:layout_width="wrap_content"
            android:layout_height="70dp"
            android:layout_weight="1"
            android:background="@color/cardview_shadow_end_color"
            android:text="Sign in"
            android:textAllCaps="false" />
    </LinearLayout>
</LinearLayout>
```
![alertdialog](/3_componentTest/screenshot/alert3.jpg)
在MainActivity的布局文件中新增一个按钮用于唤起AlertDialog，在MainActivity中通过id获取到该按钮，为其绑定点击事件，使得点击该按钮时会唤起一个AlertDialog，用先前创建的xml文件作为其布局。AlertDialog出现后，可以通过点击旁边的区域获点击cancel按钮/sign in按钮来关闭
```java
View alertdialog = findViewById(R.id.AlertDialogbtn);
alertdialog.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View layout = View.inflate(MainActivity.this, R.layout.alertdialog, null);
        builder.setView(layout);
        AlertDialog dialog = builder.show();
        layout.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.signinbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
});
```
最终实现效果如图  
![alertdialog](/3_componentTest/screenshot/alert2.jpg)
![alertdialog](/3_componentTest/screenshot/alert1.jpg)
## 3. 使用XML自定义菜单
新建一个Activity，在其布局文件内放置一个文本框，框内文字作为之后调整样式的对象  
![menu](/3_componentTest/screenshot/menu5.jpg)  
新建一个menu资源文件，定义菜单项布局层级
```java
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:title="@string/font_size">
        <menu>
            <group >
                <item android:id="@+id/big" android:title="小" />
                <item android:id="@+id/middle" android:title="中" />
                <item android:id="@+id/small" android:title="大" />
            </group>
        </menu>
    </item>
    <item android:id="@+id/plain_item" android:title="普通菜单项" />
    <item android:title="@string/font_color">
        <menu >
            <group >
                <item android:id="@+id/red" android:title="红色" />
                <item android:id="@+id/black" android:title="黑色" />
            </group>
        </menu>
    </item>
</menu>
```
在Activity文件中使用上述menu文件定义菜单的样式
```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.manu_main, menu);
    return super.onCreateOptionsMenu(menu);
}
```
为菜单中的每个选项分别绑定选择事件
```java
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.isCheckable()){
        item.setChecked(true);
    }
    switch (item.getItemId()){
        case R.id.big: txt.setTextSize(10); break;
        case R.id.middle: txt.setTextSize(16); break;
        case R.id.small: txt.setTextSize(20); break;
        case R.id.red: txt.setTextColor(Color.RED); break;
        case R.id.black: txt.setTextColor(Color.BLACK); break;
        case R.id.plain_item:
            Toast.makeText(MenuActivity.this, "点击了普通菜单项", Toast.LENGTH_SHORT).show();
            break;
    }
    return true;
}
```
最终实现效果如图  
![menu](/3_componentTest/screenshot/menu1.jpg)
![menu](/3_componentTest/screenshot/menu2.jpg)
![menu](/3_componentTest/screenshot/menu4.jpg)
![menu](/3_componentTest/screenshot/menu3.jpg)
## 4. 创建ActionMode形式的上下文菜单
新建一个Activity，在其布局文件中放置一个ListView，新建xml布局文件定义ListView每行的布局  
![ActionMode](/3_componentTest/screenshot/actionmode3.jpg)
使用SimpleAdapter填充ListView的内容
```java
List<Map<String, String>> listitems = new ArrayList<>();
for (int i = 0; i < number.length; i++){
    Map<String, String> listitem = new HashMap<>();
    listitem.put("number", number[i]);
    listitems.add(listitem);
}
SimpleAdapter adapter = new SimpleAdapter(ActionmodeActivity.this, listitems, R.layout.actionnodelist_item, new String[]{"number"}, new int[]{R.id.numbertext});
ListView list = findViewById(R.id.actionmodelist);
list.setAdapter(adapter);
```
![ActionMode](/3_componentTest/screenshot/actionmode2.jpg)
将ListView设置为多选模式，为其添加多选相关事件
```java
list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
    int count = 0;
    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
        count = list.getCheckedItemCount();
        actionMode.setTitle(count + " select");
    }
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.actionmode_menu, menu);
        return true;
    }
    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }
    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }
    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
});
```
新建一个menu资源文件作为上下文菜单的布局
```java
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item android:title="delete"
        app:showAsAction="always"
        android:id="@+id/delete"
        android:icon="@android:drawable/ic_menu_delete"/>
</menu>
```
最终实现效果如图  
![ActionMode](/3_componentTest/screenshot/actionmode.jpg)
