# 基于NotePad应用的功能拓展
## 拓展功能列表
- 时间戳功能
- 搜索功能（根据标题或内容）
- 界面UI美化
- 笔记分类（背景颜色更换）
- 排序功能

## 项目结构介绍
- 主要的类：  
**NotesList** 应用程序入口，在笔记本的首页显示笔记列表  
**NoteEditer** 实现编辑笔记内容功能  
**TitleEditor** 实现编辑笔记标题的功能  
**NotePad** 契约类，描述数据库  
**NotePadProvider** 进行数据库操作  
以上为应用原有的类，本次功能扩展新增3个类：  
**NoteSearch** 实现搜索笔记的功能  
**NoteColor** 实现改变笔记背景颜色功能  
**MyCursorAdapter** 继承自SimpleCursorAdapter，实现listview的颜色填充  
- 主要的布局文件  
**note_editor** 笔记本首页面布局  
**notelist_item** 笔记列表项布局  
**title_editor** 修改笔记主题的对话框布局  
**note_search** 搜索笔记页面布局  
**note_color** 选择笔记分类对话框布局  
- 主要的菜单文件  
**editor_option_menu** 笔记编辑页面的菜单布局  
**list_options_menu** 主页面菜单布局  

## 拓展功能详解
### 1. 时间戳功能
#### 1.1 修改列表项布局
修改layout文件夹中noteslist_item.xml的内容，为其增加线性布局框架，在其中增加一个textview组件
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    android:orientation="vertical">

    <TextView xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/text1"
        android:layout_width="match_parent"
        android:layout_height="?android:attr/listPreferredItemHeight"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:gravity="center_vertical"
        android:paddingLeft="15dip"
        android:singleLine="true"/>

    <TextView
        android:id="@+id/text2"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:gravity="center_vertical"
        android:paddingLeft="10dip"
        android:singleLine="true"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:textSize="14sp" />

</LinearLayout>
```
#### 1.2 修改NotesList.java
在PROJECTION中加入NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE字段，使得之后在搜索数据库时，能从SQLite中读取修改时间的值
```java
private static final String[] PROJECTION = new String[] {
    NotePad.Notes._ID, // 0
    NotePad.Notes.COLUMN_NAME_TITLE, // 1
    NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, //2
};
```
修改SimpleCursorAdapter的参数列表dataColumns和viewIDs的内容，使得修改时间的值可以被装配到对应的TextView中
```java
String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE , NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;
int[] viewIDs = { R.id.text1, R.id.text2};
```
#### 1.3 修改NoteEditor.java
在updateNote函数中，添加设置时间类型的处理，实现对修改时间的格式化
```java
    ContentValues values = new ContentValues();
    Long now = Long.valueOf(System.currentTimeMillis());
    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date date = new Date(now);
    String format = sf.format(date);
    values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, format);
```
#### 1.4 功能展示
![时间戳功能](/NotePad-master/screenshot/%E6%97%B6%E9%97%B4%E6%88%B3%E5%8A%9F%E8%83%BD.jpg)
### 2. 搜索功能
#### 2.1 修改菜单布局
在list_options_menu.xml布局文件中添加一个item作为搜索功能的入口
```java
 <item
    android:id="@+id/menu_search"
    android:alphabeticShortcut='a'
    android:icon="@android:drawable/ic_menu_search"
    android:title="@string/menu_add"
    android:showAsAction="always"
    tools:ignore="AppCompatResource" />
```
#### 2.2 新建布局文件
新建一个查找笔记内容的布局文件note_search.xml
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:iconifiedByDefault="false" />

    <ListView
        android:id="@+id/search_list"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```
#### 2.3 新建类文件
新建一个Activity文件NoteSearch.java，使用2.2中新建的note_search.xml作为布局，通过实现SearchView.OnQueryTextListener接口里的抽象方法完成对标题或内容的搜索功能
其中，selection1和selection2实现了对标题和内容的模糊查询搜索
```java
public class NoteSearch extends Activity implements SearchView.OnQueryTextListener {

    ListView listview;
    SQLiteDatabase sqLiteDatabase;

    private static final String[] PROJECTION = new String[]{
        NotePad.Notes._ID,
        NotePad.Notes.COLUMN_NAME_TITLE,
        NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
        NotePad.Notes.COLUMN_NAME_BACK_COLOR
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search);
        SearchView searchView = findViewById(R.id.search_view);
        Intent intent = getIntent();
        if (intent.getData() == null){
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        listview = findViewById(R.id.search_list);
        sqLiteDatabase = new NotePadProvider.DatabaseHelper(this).getReadableDatabase();
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("search");
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(this, "you choose" + s, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String selection1 = NotePad.Notes.COLUMN_NAME_TITLE + " like ? or " + NotePad.Notes.COLUMN_NAME_NOTE + " like ? ";
        String[] selection2 = {"%"+s+"%", "%"+s+"%"};
        Cursor cursor = sqLiteDatabase.query(
            NotePad.Notes.TABLE_NAME,
            PROJECTION,
            selection1,
            selection2,
            null,
            null,
            NotePad.Notes.DEFAULT_SORT_ORDER
        );
        String[] dataColums = {
                NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
        };
        int[] viewIDs = {
                R.id.text1,
                R.id.text2
        };
        
        SimpleCursorAdapter adapter  = new SimpleCursorAdapter(this, R.layout.noteslist_item, cursor, dataColums, viewIDs);
        listview.setAdapter(adapter);
        return true;
    }
}
```

#### 2.4 修改NotesList.java
在onOptionsItemSelected函数的switch中添加唤起搜索页面的case
```java
switch (item.getItemId()) {
    case R.id.menu_search:
        Intent intent = new Intent(this, NoteSearch.class);
        this.startActivity(intent);
        return true;
}
```
#### 2.5 修改AndroidManifest.xml
在清单文件中注册NoteSearch，设置label属性
```java
 <activity
    android:name=".NoteSearch"
    android:label="@string/search_note"/>
```
#### 2.6 功能展示
- 对标题的搜索：  
![搜索标题](/NotePad-master/screenshot/%E6%90%9C%E7%B4%A2%E5%8A%9F%E8%83%BD.jpg)  
- 对内容的搜索：  
![搜索内容1](/NotePad-master/screenshot/%E5%86%85%E5%AE%B9%E6%90%9C%E7%B4%A22.jpg)
![搜索内容2](/NotePad-master/screenshot/%E5%86%85%E5%AE%B9%E6%90%9C%E7%B4%A21.jpg)
### 3. 界面UI美化
#### 3.1 主题更换
在清单文件中为每个Activity标签增加theme属性以设置主题
```java
<activity
    android:name=".NotesList"
    android:exported="true"
    android:label="@string/title_notes_list"
    android:theme="@android:style/Theme.Holo.Light">
```
#### 3.2 修改数据库
首先在描述数据库的契约类NotePad.java中添加颜色字段，和不同颜色在数据库中的存储值，以数字形式储存颜色信息
```java
public static final String COLUMN_NAME_BACK_COLOR = "color";

public static final int DEFAULT_COLOR = 0;
public static final int RED_COLOR = 1;
public static final int YELLOW_COLOR = 2;
public static final int GREEN_COLOR = 3;
public static final int BLUE_COLOR = 4;
public static final int PURPLE_COLOR = 5;
public static final int PINK_COLOR = 6;
```
接着修改NotePadProvider.java文件，在创建数据库表处添加颜色字段
```java
 public void onCreate(SQLiteDatabase db) {   
    db.execSQL("CREATE TABLE " + NotePad.Notes.TABLE_NAME + " ("
        + NotePad.Notes._ID + " INTEGER PRIMARY KEY,"
        + NotePad.Notes.COLUMN_NAME_TITLE + " TEXT,"
        + NotePad.Notes.COLUMN_NAME_NOTE + " TEXT,"
        + NotePad.Notes.COLUMN_NAME_CREATE_DATE + " INTEGER,"
        + NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE + " INTEGER,"
        + NotePad.Notes.COLUMN_NAME_BACK_COLOR + " INTEGER"
        + ");");
}
```
在static中添加
```java
    sNotesProjectionMap.put(
        NotePad.Notes.COLUMN_NAME_BACK_COLOR,
        NotePad.Notes.COLUMN_NAME_BACK_COLOR
    );
```
在insert函数中添加处理，使颜色值为空时，为其赋默认值
```java
    if (values.containsKey(NotePad.Notes.COLUMN_NAME_BACK_COLOR) == false) {
        values.put(NotePad.Notes.COLUMN_NAME_BACK_COLOR, NotePad.Notes.DEFAULT_COLOR);
    }
```
最后在NotesList.java中的PROJECTION中添加颜色字段，使列表初始化时可以查询颜色信息
```java
private static final String[] PROJECTION = new String[] {
    NotePad.Notes._ID, // 0
    NotePad.Notes.COLUMN_NAME_TITLE, // 1
    NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, //2
    NotePad.Notes.COLUMN_NAME_BACK_COLOR //3
};
```
#### 3.3 新建类文件
获得数据库中的颜色信息后，为了将颜色填充到ListView的列表项中，新建一个自定义类文件MyCursorAdapter.java继承SimpleCursorAdapter
```java
public class MyCursorAdapter extends SimpleCursorAdapter {
    public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        @SuppressLint("Range") int x = cursor.getInt(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_BACK_COLOR));
        switch (x){
            case NotePad.Notes.DEFAULT_COLOR:
                view.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
            case NotePad.Notes.YELLOW_COLOR:
                view.setBackgroundColor(Color.rgb(255, 255, 204));
                break;
            case NotePad.Notes.BLUE_COLOR:
                view.setBackgroundColor(Color.rgb(204, 255, 255));
                break;
            case NotePad.Notes.GREEN_COLOR:
                view.setBackgroundColor(Color.rgb(204, 255, 204));
                break;
            case NotePad.Notes.RED_COLOR:
                view.setBackgroundColor(Color.rgb(255, 204, 204));
                break;
            case NotePad.Notes.PURPLE_COLOR:
                view.setBackgroundColor(Color.rgb(204, 204, 255));
                break;
            case NotePad.Notes.PINK_COLOR:
                view.setBackgroundColor(Color.rgb(255, 204, 255));
                break;
            default:
                view.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
        }
    }
}
```
#### 3.4 修改列表的适配器
将NotesList.java和NoteSearch.java中使用的适配器从SimpleCursorAdapter改为3.3中新建的MyCursorAdapter
```java
 MyCursorAdapter adapter  = new MyCursorAdapter(this, R.layout.noteslist_item, cursor, dataColums, viewIDs);
```
#### 3.5 功能展示
由于还没有修改颜色的功能，所以目前所有的列表项都是默认颜色白色，可以通过在SQLite中修改color字段的值来修改列表项的颜色
![UI美化](/NotePad-master/screenshot/UI%E7%BE%8E%E5%8C%96.jpg)

### 4. 笔记分类（背景颜色更换）
#### 4.1 新建类文件
创建一个Activity文件NoteColor.java，在编辑笔记的页面用于选择笔记的分类
```java
public class NoteColor extends Activity {
    private Cursor mCursor;
    private Uri mUri;
    private int color;
    private static final int COLUMN_INDEX_TITIE = 1;
    private static final String[] PROJECTION = new String[]{
            NotePad.Notes._ID,
            NotePad.Notes.COLUMN_NAME_BACK_COLOR
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_color);
        mUri = getIntent().getData();
        mCursor = managedQuery(
                mUri,
                PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCursor != null) {
            mCursor.moveToFirst();
            color = mCursor.getInt(COLUMN_INDEX_TITIE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ContentValues values = new ContentValues();
        values.put(NotePad.Notes.COLUMN_NAME_BACK_COLOR, color);
        getContentResolver().update(mUri, values, null, null);
    }
    public void white(View view){
        color = NotePad.Notes.DEFAULT_COLOR;
        finish();
    }
    public void red(View view){
        color = NotePad.Notes.RED_COLOR;
        finish();
    }
    ......
}
```
#### 4.2 新建布局文件
为4.1中新建的Activity创建布局文件note_color.xml，放置7个button，对界面进行布局，为每个按钮绑定onClick属性，点击按钮时将会调用对应的函数
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">
    <Button
        android:id="@+id/white"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:background="#FFFFFF"
        android:onClick="white"
        android:text="普通笔记"
        android:textColor="#404040" />
        ......
</LinearLayout>
```
#### 4.3 修改菜单布局
在editor_options_menu.xml布局文件中添加一个item作为分类功能的入口：
```java
<item android:id="@+id/menu_color"
    android:icon="@android:drawable/btn_star"
    android:title="@string/menu_color"
    android:showAsAction="always"
    tools:ignore="AppCompatResource" />
```
#### 4.4 修改NoteEditor.java
在PROJECTION中添加颜色字段，使编辑笔记界面可以获取到数据库中的颜色数据
```java
private static final String[] PROJECTION =
    new String[] {
        NotePad.Notes._ID,
        NotePad.Notes.COLUMN_NAME_TITLE,
        NotePad.Notes.COLUMN_NAME_NOTE,
        NotePad.Notes.COLUMN_NAME_BACK_COLOR
};
```
在onResume函数中添加背景颜色的相关处理，**onResume()方法在正常启动时会在onStart()执行后被调用，在Acitivity从Pause状态转化到Active状态时也会被调用**，由此，在从首页进入编辑界面时和从选择分类界面回到编辑界面时，都会对页面颜色进行重新设置，保证了页面颜色能跟随最新的修改状态
```java
@SuppressLint("Range") int x = mCursor.getInt(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_BACK_COLOR));
    switch (x){
        case NotePad.Notes.DEFAULT_COLOR:
            mText.setBackgroundColor(Color.rgb(255, 255, 255));
            break;
        case NotePad.Notes.YELLOW_COLOR:
            mText.setBackgroundColor(Color.rgb(255, 255, 204));
            break;
        case NotePad.Notes.BLUE_COLOR:
            mText.setBackgroundColor(Color.rgb(204, 255, 255));
            break;
        case NotePad.Notes.GREEN_COLOR:
            mText.setBackgroundColor(Color.rgb(204, 255, 204));
            break;
        case NotePad.Notes.PURPLE_COLOR:
            mText.setBackgroundColor(Color.rgb(204, 204, 255));
            break;
        case NotePad.Notes.PINK_COLOR:
            mText.setBackgroundColor(Color.rgb(255, 204, 255));
            break;
        case NotePad.Notes.RED_COLOR:
            mText.setBackgroundColor(Color.rgb(255, 204, 204));
            break;
        default:
            mText.setBackgroundColor(Color.rgb(255, 255, 255));
            break;
    }
```

添加函数changeColor用于唤起选择分类界面：
```java
private final void changeColor() {
    Intent intent = new Intent(null, mUri);
    intent.setClass(NoteEditor.this, NoteColor.class);
    NoteEditor.this.startActivity(intent);
}
```
在onOptionsItemSelected函数的switch中添加case来调用changeColor函数：
```java
    case R.id.menu_color:
        changeColor();
        break;
```
#### 4.5 修改AndroidManifest.xml
在清单文件中注册NoteColor，将其主题定义为对话框样式
```java
 <activity
    android:name=".NoteColor"
    android:label="@string/change_color"
    android:theme="@android:style/Theme.Holo.Light.Dialog" />
```
#### 4.6 功能展示
修改笔记“测试2”的分类（背景颜色）  
![分类功能1](/NotePad-master/screenshot/%E5%88%86%E7%B1%BB%E5%8A%9F%E8%83%BD3.jpg)
![分类功能2](/NotePad-master/screenshot/%E5%88%86%E7%B1%BB%E5%8A%9F%E8%83%BD1.jpg)
![分类功能3](/NotePad-master/screenshot/%E5%88%86%E7%B1%BB%E5%8A%9F%E8%83%BD2.jpg)

### 5. 排序功能
#### 5.1 修改菜单布局
在list_options_menu.xml中添加：
```java
<item
    android:id="@+id/menu_sort"
    android:alphabeticShortcut='a'
    android:icon="@android:drawable/ic_menu_sort_by_size"
    android:title="@string/menu_add"
    android:showAsAction="always"
    tools:ignore="AppCompatResource">
    <menu>
        <item android:title="@string/sort3"
            android:id="@+id/sort3"/>
        <item android:title="@string/sort1"
            android:id="@+id/sort1"/>
        <item android:title="@string/sort2"
            android:id="@+id/sort2"/>
    </menu>
</item>
```
效果如图：
![排序菜单](/NotePad-master/screenshot/%E6%8E%92%E5%BA%8F%E5%8A%9F%E8%83%BD.jpg)
#### 5.2 修改NotesList.java
在onOptionsItemSelected函数的switch中添加修改列表项排列顺序的case
```java
case R.id.sort1:
//按照修改时间排序
    cursor = managedQuery(
        getIntent().getData(),
        PROJECTION,
        null,
        null,
        NotePad.Notes.DEFAULT_SORT_ORDER
    );
    adapter = new MyCursorAdapter(
        this,
        R.layout.noteslist_item,
        cursor,
        dataColumns,
        viewIDs
    );
    setListAdapter(adapter);
    return true;
```
```java
case R.id.sort2:
//按照分类排序
    cursor = managedQuery(
        getIntent().getData(),
        PROJECTION,
        null,
        null,
        NotePad.Notes.COLUMN_NAME_BACK_COLOR
    );
    adapter = new MyCursorAdapter(
        this,
        R.layout.noteslist_item,
        cursor,
        dataColumns,
        viewIDs
    );
    setListAdapter(adapter);
    return true;
```
```java
case R.id.sort3:
//按照创建时间排序
    cursor = managedQuery(
        getIntent().getData(),
        PROJECTION,
        null,
        null,
        NotePad.Notes._ID
    );
    adapter = new MyCursorAdapter(
        this,
        R.layout.noteslist_item,
        cursor,
        dataColumns,
        viewIDs
    );
    setListAdapter(adapter);
    return true;
```
#### 5.3 功能展示
- 修改时间排序  
![修改时间排序](/NotePad-master/screenshot/%E4%BF%AE%E6%94%B9%E6%97%B6%E9%97%B4%E6%8E%92%E5%BA%8F.jpg)  
- 分类排序  
![分类排序](/NotePad-master/screenshot/%E5%88%86%E7%B1%BB%E6%8E%92%E5%BA%8F.jpg)  
- 创建时间排序  
![创建时间排序](/NotePad-master/screenshot/%E5%88%9B%E5%BB%BA%E6%97%B6%E9%97%B4%E6%8E%92%E5%BA%8F.jpg)
