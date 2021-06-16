package uz.texnopos.retrieveimagefirebase

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import uz.texnopos.retrieveimagefirebase.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.getImage.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Fetching image ... ")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val imageName = viewBinding.etImageId.text.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName.png")
            val localFile = File.createTempFile("tempImage", "png")
            storageRef.getFile(localFile)
                .addOnSuccessListener {

                    if(progressDialog.isShowing){
                        progressDialog.dismiss()
                    }

                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    viewBinding.imageView.setImageBitmap(bitmap)
                }
                .addOnFailureListener {
                    if(progressDialog.isShowing){
                        progressDialog.dismiss()
                    }
                    Toast.makeText(this, "Failed to retrieve image !", Toast.LENGTH_LONG).show()
                }
        }
    }
}