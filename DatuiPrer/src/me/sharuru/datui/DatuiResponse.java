package me.sharuru.datui;

import com.google.gson.annotations.SerializedName;

public class DatuiResponse {

    @SerializedName("created_at")
    private String createdAt;
    private String height;
    @SerializedName("image_label_nana")
    private String imageLabelNana;
    @SerializedName("image_label_socks_color")
    private String imageLabelSocksColor;
    @SerializedName("image_label_socks_height")
    private String imageLabelSocksHeight;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("s3_image_id")
    private String s3ImageId;
    private String width;
    @SerializedName("_id")
    private String id;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImageLabelNana() {
        return imageLabelNana;
    }

    public void setImageLabelNana(String imageLabelNana) {
        this.imageLabelNana = imageLabelNana;
    }

    public String getImageLabelSocksColor() {
        return imageLabelSocksColor;
    }

    public void setImageLabelSocksColor(String imageLabelSocksColor) {
        this.imageLabelSocksColor = imageLabelSocksColor;
    }

    public String getImageLabelSocksHeight() {
        return imageLabelSocksHeight;
    }

    public void setImageLabelSocksHeight(String imageLabelSocksHeight) {
        this.imageLabelSocksHeight = imageLabelSocksHeight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getS3ImageId() {
        return s3ImageId;
    }

    public void setS3ImageId(String s3ImageId) {
        this.s3ImageId = s3ImageId;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DatuiResponse{" +
                "createdAt='" + createdAt + '\'' +
                ", height='" + height + '\'' +
                ", imageLabelNana='" + imageLabelNana + '\'' +
                ", imageLabelSocksColor='" + imageLabelSocksColor + '\'' +
                ", imageLabelSocksHeight='" + imageLabelSocksHeight + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", s3ImageId='" + s3ImageId + '\'' +
                ", width='" + width + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}