package com.example.fbi.ui.camera

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fbi.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : Fragment() {

    private lateinit var cameraViewModel: CameraViewModel
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath : String
    private lateinit var mCtx : Context

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mCtx = inflater.context
        startCapture()
        cameraViewModel =
                ViewModelProviders.of(this).get(CameraViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_camera, container, false)
        val textView: TextView = root.findViewById(R.id.text_camera)
        cameraViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

//        camera_picture.setOnClickListener {
//            val builder = AlertDialog.Builder(mCtx)
//            val dialogView = layoutInflater.inflate(R.layout.check_book_popup, null)
//            val dialogText = dialogView.findViewById<TextView>(R.id.tv_check_book_popup)
//
//            builder.setView(dialogView)
//                .setPositiveButton("확인") { dialogInterface, i ->
//                    Toast.makeText(mCtx, "Pushed save", Toast.LENGTH_SHORT).show()
//                }
//                .setNegativeButton("취소") { dialogInterface, i ->
//                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
//                }
//                .show()
//                .window?.setLayout(500,400)
//        }

        return root
    }

    // 사진을 찍고 나서 이미지를 파일로 저장해주는 함수
    private fun createImageFile() : File {
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply{
            currentPhotoPath = absolutePath
        }
    }

    fun startCapture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                }catch(ex: IOException){
                    null
                }
                photoFile?.also{
                    val photoURI : Uri = FileProvider.getUriForFile(
                        activity!!,
                        "com.example.fbi.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val file = File(currentPhotoPath)
            if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(mCtx.contentResolver, Uri.fromFile(file))
                camera_picture?.setImageBitmap(bitmap)
            } else {
                val decode = ImageDecoder.createSource(
                    mCtx.contentResolver,
                    Uri.fromFile(file)
                )
                val bitmap = ImageDecoder.decodeBitmap(decode)
                camera_picture?.setImageBitmap(bitmap)
            }
        }
    }


//    private fun showSettingPopup() {
//
//        val inflater = mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.check_book_popup, null)
//        val textView: TextView = view?.findViewById(R.id.tv_check_book_popup)
//        textView.text = "팝업 샘플 입니다~"
//
//        val alertDialog = AlertDialog.Builder(mCtx)
//            .setTitle("AlertDialog Exam")
//            .setPositiveButton("OK") { dialog, which ->
//                Toast.makeText(mCtx, "Pushed save", Toast.LENGTH_SHORT).show()
//            }
//            .setNeutralButton("Cancel", null)
//            .create()
//
//        alertDialog.setView(view)
//        alertDialog.show()
//        alertDialog.window?.setLayout(500,400)
//
//    }
}
