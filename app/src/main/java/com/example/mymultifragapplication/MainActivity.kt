package com.example.mymultifragapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymultifragapplication.databinding.ActivityMainBinding

// yujin commit test 2
//test

// test - minsu


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //up버튼에서도 사용하기 때문에 전역으로 뺌

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        //네비게이션 컨트롤러
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        //top 레벨 Fragment id
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mapFragment, R.id.mainFragment, R.id.tomorrowMapFragment) //이 Fragment 화면 상단에는 up버튼 안보임
        )
        setupActionBarWithNavController(navController, appBarConfiguration) // 액션바
        binding.bottomNav.setupWithNavController(navController) //하단 버튼 설정
        setContentView(binding.root)
    }

    // up버튼 설정
    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}