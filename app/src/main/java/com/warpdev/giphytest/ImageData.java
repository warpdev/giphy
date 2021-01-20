package com.warpdev.giphytest;

import com.google.gson.annotations.SerializedName;

/**
 * Giphy에서 받아온 이미지에 대한 데이터를 나타내기 위한 클래스
 *
 * @author warpdev
 */
public class ImageData {
    /** 각 이미지의 고유 ID */
    @SerializedName("id")
    private String id;

    @SerializedName("images")
    private GifImage images;

    /** 최적의 옵션으로 선택된 이미지 옶션 */
    private GifImage.AboutGif aboutGif;

    /** 해당 이미지가 Favorite되었는지 여부 */
    private boolean isFavorite;

    public String getId() {
        return id;
    }

    public GifImage getImages() {
        return images;
    }

    public GifImage.AboutGif getAboutGif() {
        return aboutGif;
    }

    public void setAboutGif(GifImage.AboutGif aboutGif) {
        this.aboutGif = aboutGif;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    /**
     * FavoritePage에서 사용하기 위해 만든 생성자
     * 아래 파라미터 설명은 FavoritePage에서 넘겨주는 것을 기준으로 작성
     *
     * @param id            Favorite 목록에 있는 이미지 고유 id
     * @param aboutGif      height, width, url를 담고있는 object
     * @param isFavorite    Favorite 여부. 여기에서는 기본적으로 true를 보통 받아오게 된다.
     */
    public ImageData(String id, GifImage.AboutGif aboutGif, boolean isFavorite) {
        this.id = id;
        this.aboutGif = aboutGif;
        this.isFavorite = isFavorite;
    }

    static class GifImage {
        /**
         * GifImage에 대한 정보들을 담는 Class
         */
        @SerializedName("original")
        private Original original;

        @SerializedName("preview_gif")
        private PreviewGif previewGif;

        public Original getOriginal() {
            return original;
        }

        public PreviewGif getPreviewGif() {
            return previewGif;
        }


        static class Original extends AboutGif {
            public Original(int height, int width, String url) {
                super(height, width, url);
            }
        }

        static class PreviewGif extends AboutGif {
            public PreviewGif(int height, int width, String url) {
                super(height, width, url);
            }
        }

        static class AboutGif {

            @SerializedName("height")
            private int height;

            @SerializedName("width")
            private int width;

            @SerializedName("url")
            private String url;

            public int getHeight() {
                return height;
            }

            public int getWidth() {
                return width;
            }

            public String getUrl() {
                return url;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public AboutGif(int height, int width, String url) {
                this.height = height;
                this.width = width;
                this.url = url;
            }
        }

    }
}
