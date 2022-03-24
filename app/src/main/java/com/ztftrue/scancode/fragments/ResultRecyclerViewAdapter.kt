package com.ztftrue.scancode.fragments

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.barcode.common.Barcode
import com.ztftrue.scancode.databinding.FragmentItemBinding

/**
 * TODO: Replace the implementation with code for your data type.
 */
class ResultRecyclerViewAdapter(
    private val values: List<Barcode>
) : RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val phoneType = arrayOf(
        "TYPE_UNKNOWN",
        "TYPE_WORK",
        "TYPE_HOME",
        "TYPE_FAX",
        "TYPE_MOBILE"
    )
    private val wifiType = arrayOf(
        "",
        "TYPE_OPEN",
        "TYPE_WPA",
        "TYPE_WEP",
    )
    private val emailType = arrayOf(
        "TYPE_UNKNOWN",
        "TYPE_WORK",
        "TYPE_HOME",
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val barcode = values[position]
//        val bounds = barcode.boundingBox
//        val corners = barcode.cornerPoints
        val rawValue = barcode.rawValue
        when (barcode.valueType) {
            Barcode.TYPE_WIFI -> {
                val ssid = barcode.wifi!!.ssid
                val password = barcode.wifi!!.password
                val type = barcode.wifi!!.encryptionType
                holder.message1.text = ssid.toString()
                holder.message2.text = password.toString()
                holder.message3.text = wifiType[type]
            }
            Barcode.TYPE_URL -> {
                val title = barcode.url!!.title
                val url = barcode.url!!.url
                holder.message1.text = title.toString()
                holder.message2.text = url.toString()
            }
            Barcode.TYPE_TEXT -> {
                holder.message1.text = barcode.rawValue.toString()
            }
            Barcode.TYPE_CONTACT_INFO -> {
                Log.i("TAG", barcode.rawValue.toString())
                val title = barcode.contactInfo?.title
                val name = barcode.contactInfo?.name
                val emails = barcode.contactInfo?.emails
                val addresses = barcode.contactInfo?.addresses
                val organization = barcode.contactInfo?.organization
                val phones = barcode.contactInfo?.phones
                val urls = barcode.contactInfo?.urls
                holder.message1.text = title.toString()
                holder.message2.text = name.toString()
                holder.message3.text = emails.toString()
                holder.message4.text =
                    addresses.toString() + "\n" + "\n" + organization.toString() + "\n" + phones.toString() + "\n" + urls.toString()
            }
            Barcode.TYPE_EMAIL -> {
                val address = barcode.email?.address
                val body = barcode.email?.body
                val subject = barcode.email?.subject
                val type = barcode.email!!.type
                holder.message1.text = address.toString()
                holder.message2.text = body.toString()
                holder.message3.text = subject.toString()
                holder.message4.text = emailType[type]
            }
            /**
             * TYPE_UNKNOWN
            TYPE_WORK
            TYPE_HOME
             */
            Barcode.TYPE_PHONE -> {
                val number = barcode.phone?.number
                val type = barcode.phone?.type
                holder.message1.text = number.toString()
                holder.message2.text = phoneType[type!!]
            }

            Barcode.TYPE_SMS -> {
                val phoneNumber = barcode.sms?.phoneNumber
                val message = barcode.sms?.message
                holder.message1.text = phoneNumber.toString()
                holder.message2.text = message.toString()
            }

            Barcode.TYPE_GEO -> {
                val lat = barcode.geoPoint?.lat
                val lng = barcode.geoPoint?.lng
                holder.message1.text = lat.toString()
                holder.message2.text = lng.toString()
            }
            Barcode.TYPE_CALENDAR_EVENT -> {
                val start = barcode.calendarEvent?.start
                val end = barcode.calendarEvent?.end
                val summary = barcode.calendarEvent?.summary
                val location = barcode.calendarEvent?.location
                val organizer = barcode.calendarEvent?.organizer
                val description = barcode.calendarEvent?.description
                val status = barcode.calendarEvent?.status
                holder.message1.text =
                    start.toString() + "-" + end.toString() + "\n" + organizer.toString()
                holder.message2.text = location.toString()
                holder.message3.text = summary.toString() + "\n" + description.toString()
                holder.message4.text = status.toString()
            }
            Barcode.TYPE_DRIVER_LICENSE -> {
                val addressCity = barcode.driverLicense?.addressCity
                val addressState = barcode.driverLicense?.addressState
                val addressStreet = barcode.driverLicense?.addressStreet
                val addressZip = barcode.driverLicense?.addressZip
                val birthDate = barcode.driverLicense?.birthDate
                val documentType = barcode.driverLicense?.documentType
                val expiryDate = barcode.driverLicense?.expiryDate
                val firstName = barcode.driverLicense?.firstName
                val gender = barcode.driverLicense?.gender
                val issueDate = barcode.driverLicense?.issueDate
                val issuingCountry = barcode.driverLicense?.issuingCountry
                val lastName = barcode.driverLicense?.lastName
                val licenseNumber = barcode.driverLicense?.licenseNumber
                val middleName = barcode.driverLicense?.middleName
                holder.message1.text = addressCity + "\n" +
                        addressState + "\n" +
                        addressStreet + "\n" +
                        addressZip + "\n" +
                        birthDate + "\n" +
                        documentType + "\n" +
                        expiryDate + "\n" +
                        firstName + "\n" +
                        gender + "\n" +
                        issueDate + "\n" +
                        issuingCountry + "\n" +
                        lastName + "\n" +
                        licenseNumber + "\n" +
                        middleName + "\n"
            }
            else -> {
                holder.message1.text = rawValue.toString()
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val message1: TextView = binding.message1
        val message2: TextView = binding.message2
        val message3: TextView = binding.message3
        val message4: TextView = binding.message4


    }
    /**
     *
    // Handle share button press
    fragmentGalleryBinding.shareButton.setOnClickListener {

    mediaList.getOrNull(fragmentGalleryBinding.photoViewPager.currentItem)
    ?.let { mediaFile ->

    // Create a sharing intent
    val intent = Intent().apply {
    // Infer media type from file extension
    val mediaType = MimeTypeMap.getSingleton()
    .getMimeTypeFromExtension(mediaFile.extension)
    // Get URI from our FileProvider implementation
    val uri = FileProvider.getUriForFile(
    view.context, BuildConfig.APPLICATION_ID + ".provider", mediaFile
    )
    // Set the appropriate intent extra, type, action and flags
    putExtra(Intent.EXTRA_STREAM, uri)
    type = mediaType
    action = Intent.ACTION_SEND
    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    // Launch the intent letting the user choose which app to share with
    startActivity(Intent.createChooser(intent, getString(R.string.share_hint)))
    }
    }

    // Handle delete button press
    fragmentGalleryBinding.deleteButton.setOnClickListener {

    mediaList.getOrNull(fragmentGalleryBinding.photoViewPager.currentItem)
    ?.let { mediaFile ->

    AlertDialog.Builder(view.context, android.R.style.Theme_Material_Dialog)
    .setTitle(getString(R.string.delete_title))
    .setMessage(getString(R.string.delete_dialog))
    .setIcon(android.R.drawable.ic_dialog_alert)
    .setPositiveButton(android.R.string.yes) { _, _ ->

    // Delete current photo
    mediaFile.delete()

    // Send relevant broadcast to notify other apps of deletion
    MediaScannerConnection.scanFile(
    view.context, arrayOf(mediaFile.absolutePath), null, null
    )

    // Notify our view pager

    // If all photos have been deleted, return to camera
    if (mediaList.isEmpty()) {
    Navigation.findNavController(
    requireActivity(),
    R.id.fragment_container
    ).navigateUp()
    }

    }

    .setNegativeButton(android.R.string.no, null)
    .create().showImmersive()
    }
    }
     */
}