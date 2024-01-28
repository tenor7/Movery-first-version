package com.example.mediatec;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.os.IResultReceiver;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mediatec.Models.AllCategory;
import com.example.mediatec.Models.BannerMovies;
import com.example.mediatec.Models.CategoryItem;
import com.example.mediatec.Models.Film;
import com.example.mediatec.Models.User;
import com.example.mediatec.adapter.BannerMoviesPagerAdapter;
import com.example.mediatec.adapter.MainRecyclerAdapter;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FirstPageActivity extends AppCompatActivity {
    Button btnAdd;
    ImageView exit;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    ConstraintLayout firstPage;
    RelativeLayout add;
    Intent intent = getIntent();
    //String email = intent.getStringExtra("Email");

    Film film;
    DatabaseReference reff;

    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerMoviesViewPager;
    List<BannerMovies> homeBannerList;
    List<BannerMovies> tvShowBannerList;
    List<BannerMovies> movieBannerList;
    List<BannerMovies> cartoonBannerList;
    Timer sliderTimer;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategory> allCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");

        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);

        if(email.equals("5@mail.ru")){
            btnAdd = findViewById(R.id.btnAdd);
            btnAdd.setVisibility(View.VISIBLE);
        }

        homeBannerList = new ArrayList<>();
        homeBannerList.add(new BannerMovies(1, "LDR", "https://www.kino-teatr.ru/movie/poster/138431/97445.jpg", ""));
        homeBannerList.add(new BannerMovies(2, "Бэтмен", "https://www.winwallpapers.net/w1/2014/01/Batman-Begins-2005-Wallpapers-3.jpg", ""));
        homeBannerList.add(new BannerMovies(3, "Мстители", "https://b1.filmpro.ru/c/567289.jpg", ""));
        homeBannerList.add(new BannerMovies(4, "Звездные войны", "https://i1.wp.com/www.heyuguys.com/images/2015/10/star-wars-the-force-awakens-poster-landscape.jpg?fit=1536%2C864&ssl=1", ""));
        homeBannerList.add(new BannerMovies(5, "Железный человек", "https://media.kg-portal.ru/movies/i/ironman/posters/ironman_7.jpg", ""));

        tvShowBannerList = new ArrayList<>();
        tvShowBannerList.add(new BannerMovies(1, "Классный мюзикл", "https://www.mtishows.com/sites/default/files/show/hero/000556_hero.jpg", ""));
        tvShowBannerList.add(new BannerMovies(2, "Кэмп рок", "https://wallpaperaccess.com/full/3007686.png", "4"));
        tvShowBannerList.add(new BannerMovies(3, "Наследники", "https://cdn.wallpapersafari.com/65/66/UP9Ocm.jpg", ""));

        movieBannerList = new ArrayList<>();
        movieBannerList.add(new BannerMovies(1, "Дамбо", "https://botanicgarden.wales/wp-content/uploads/2019/07/dumbo-2019-tim-burton-movie-review-2.jpg", ""));
        movieBannerList.add(new BannerMovies(2, "Кристофер Робин", "https://honey.com/images/default/CR-NHB-Ad-Horizontal_Cropped.jpg", ""));
        movieBannerList.add(new BannerMovies(3, "Круэлла", "https://miro.medium.com/max/3132/1*6Xm293DP4c8r_w6yeEFLKw.jpeg", "https://mm.multikland.net/embed/kp/229861?soundBlock=4,5,158,180&oneSeason=true&season=1&episode=1"));

        cartoonBannerList = new ArrayList<>();
        cartoonBannerList.add(new BannerMovies(1, "Человек паук", "https://wallpapershome.ru/images/pages/pic_h/21159.jpg", ""));
        cartoonBannerList.add(new BannerMovies(2, "Железный человек", "https://msteranko.files.wordpress.com/2011/03/ironman_poster2.jpg", ""));
        cartoonBannerList.add(new BannerMovies(3, "Стражи галактики", "https://marvellessmarian.files.wordpress.com/2015/04/guardians-of-the-galaxy-2014-movie-poster-cover.jpg", ""));
        cartoonBannerList.add(new BannerMovies(4, "Мстители", "https://images.squarespace-cdn.com/content/v1/592b020a8419c2e1dd1199dc/1524536685475-NALVUGZ0EJTQ9W29TQ90/ke17ZwdGBToddI8pDm48kOdlMSuc6l3bg4_O3p1DNccUqsxRUqqbr1mOJYKfIPR7LoDQ9mXPOjoJoqy81S2I8N_N4V1vUb5AoIIIbLZhVYxCRW4BPu10St3TBAUQYVKcxzwZuf9fRNKkUMm03yynrdSj3pwwD7zb1cvsHRaCg60C1WB8ANBZkVcGAwv6VHsQ/Infinity-War-Movie-New-Presales-Record.jpg", ""));
        cartoonBannerList.add(new BannerMovies(5, "Тор Рагнарек", "https://babbletop.com/wp-content/uploads/2017/07/ThorRagnarok.jpg", ""));


        setBannerMoviesPagerAdapter(homeBannerList);

        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(tvShowBannerList);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(movieBannerList);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(cartoonBannerList);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(homeBannerList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        List<CategoryItem> homeCatListItem1 = new ArrayList<>();
        homeCatListItem1.add(new CategoryItem(1, "Король лев", "https://i.pinimg.com/originals/3f/91/74/3f9174c6b303e839c6526c3a175d7af9.jpg", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"));
        homeCatListItem1.add(new CategoryItem(2, "Как приручить дракона 2", "https://images-na.ssl-images-amazon.com/images/I/511FoON0sfL._AC_.jpg", ""));
        homeCatListItem1.add(new CategoryItem(3, "Тачки", "https://thumbs.dfs.ivi.ru/storage39/contents/4/6/312cb365dfd73b6895dac1fc5606e7.jpg", ""));
        homeCatListItem1.add(new CategoryItem(4, "Зверополис", "https://thumbs.dfs.ivi.ru/storage4/contents/3/3/6bc815a83d8184c9a332267bcf4b63.jpg", ""));
        homeCatListItem1.add(new CategoryItem(5, "Холодное сердце", "https://i.ebayimg.com/images/g/H6MAAOSwhYNdqND6/s-l1600.jpg", ""));

        List<CategoryItem> homeCatListItem2 = new ArrayList<>();
        homeCatListItem2.add(new CategoryItem(1, "Джон Уик", "https://www.film.ru/sites/default/files/movies/posters/john_wick_xlg.jpg", ""));
        homeCatListItem2.add(new CategoryItem(2, "Апгрейд", "https://thumbs.dfs.ivi.ru/storage38/contents/4/7/ce836109ef8a63711a22b52c559d4c.jpg", ""));
        homeCatListItem2.add(new CategoryItem(3, "Такси 2", "https://thumbs.dfs.ivi.ru/storage37/contents/5/b/a8f97e1b650154e68515bba66d1e21.jpg", ""));
        homeCatListItem2.add(new CategoryItem(4, "Терминатор 2", "https://thumbs.dfs.ivi.ru/storage32/contents/8/a/66558a576cbeb467619d73d1ff5b50.jpg", ""));
        homeCatListItem2.add(new CategoryItem(5, "Живая сталь", "https://thumbs.dfs.ivi.ru/storage28/contents/c/6/759dcb4c92c48c67f61e8b10e574c5.jpg", ""));

        List<CategoryItem> homeCatListItem3 = new ArrayList<>();
        homeCatListItem3.add(new CategoryItem(1, "Оно", "https://thumbs.dfs.ivi.ru/storage33/contents/f/6/ab3f57dd8462deaf38e26dd8896ae2.jpg", ""));
        homeCatListItem3.add(new CategoryItem(2, "13 грехов", "https://thumbs.dfs.ivi.ru/storage38/contents/5/0/aae95a5b2cc700f5262b12125820f7.jpg", ""));
        homeCatListItem3.add(new CategoryItem(3, "Пила 8", "https://thumbs.dfs.ivi.ru/storage2/contents/6/9/0878170e374a2ef5a477f9a9ac3051.jpg", ""));
        homeCatListItem3.add(new CategoryItem(4, "Веном", "https://thumbs.dfs.ivi.ru/storage8/contents/9/0/5517f2eb675b7e4785ebe48972eb3d.jpg", ""));
        homeCatListItem3.add(new CategoryItem(5, "Спираль", "https://giknutye.ru/wp-content/uploads/2020/02/Spiral-poster-e1580917050632.jpeg", ""));

        List<CategoryItem> homeCatListItem4 = new ArrayList<>();
        homeCatListItem4.add(new CategoryItem(1, "Игра в имитацию", "https://thumbs.dfs.ivi.ru/storage6/contents/9/b/c50723f5f339fbaaec71c79f323f83.jpg", ""));
        homeCatListItem4.add(new CategoryItem(2, "Прогулка среди могил", "https://thumbs.dfs.ivi.ru/storage33/contents/f/9/30742e2ebe7a88b805d608e76375c6.jpg", ""));
        homeCatListItem4.add(new CategoryItem(3, "Шерлок Холмс", "https://thumbs.dfs.ivi.ru/storage28/contents/9/8/28252fd2c21f9ed09083fa0f8fb086.jpg", ""));
        homeCatListItem4.add(new CategoryItem(4, "Паркер", "https://thumbs.dfs.ivi.ru/storage28/contents/5/8/487186995c9bada978ed89fac1a2f7.jpg", ""));
        homeCatListItem4.add(new CategoryItem(5, "Красивый, плохой, злой", "https://thumbs.dfs.ivi.ru/storage2/contents/0/6/e66db4b9c5d3dc095715bbc5377916.jpg", "3"));

        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory(1, "Мультфильмы Disney", homeCatListItem1));
        allCategoryList.add(new AllCategory(2, "Лучшие боевики тысячелетия", homeCatListItem2));
        allCategoryList.add(new AllCategory(3, "Триллеры-ужасы", homeCatListItem3));
        allCategoryList.add(new AllCategory(4, "Фильмы, снятые на основе книг", homeCatListItem4));


        setMainRecycler(allCategoryList);

        ////////////////////////////////////////////////////////////////////////////////////
        btnAdd = findViewById(R.id.btnAdd);
        firstPage = findViewById(R.id.firstPage);
        reff = FirebaseDatabase.getInstance().getReference("User");


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddWindow();
            }
        });

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FirstPageActivity.this, MainActivity.class));
                finish();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////
    }

    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList) {
        bannerMoviesViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviesPagerAdapter = new BannerMoviesPagerAdapter(this, bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviesPagerAdapter);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager);

        //slider images
        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager, true);
    }

    class AutoSlider extends TimerTask {
        @Override
        public void run() {
            FirstPageActivity.this.runOnUiThread(() -> {
                if (bannerMoviesViewPager.getCurrentItem() < homeBannerList.size() - 1) {
                    bannerMoviesViewPager.setCurrentItem(bannerMoviesViewPager.getCurrentItem() + 1);
                } else {
                    bannerMoviesViewPager.setCurrentItem(0);
                }
            });
        }
    }

    public void setMainRecycler(List<AllCategory> allCategoryList) {
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

    private void setScrollDefaultState() {
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0, 0);
        appBarLayout.setExpanded(true);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showAddWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(this);
        View add_window = inflater.inflate(R.layout.add_window, null);
        dialog.setView(add_window);

        Intent intent =new Intent(FirstPageActivity.this, Delete.class);
        startActivity(intent);
        finish();
    }

}