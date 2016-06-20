/**
     * 
     * @param display
     * @param object
     *            a image object or a color object
     * @param width
     *            if object is a image object the parameter is invalid, if this
     *            object is a color object the parameter is a new image width
     * @param height
     *            if object is a image object the parameter is invalid, if this
     *            object is a color object the parameter is a new image height
     * @return image a image object
     */
    public static Image customImage(Display display, Object object, int width, int height) {
        if (object == null) {
            return null;
        }
        if (object.getClass() == Image.class) {
            return (Image) object;
        } else if (object.getClass() == Color.class) {
            Color color = (Color) object;
            Image image = new Image(display, width, height);
            GC gc = new GC(image);
            gc.setBackground(color);
            gc.fillRectangle(0, 0, width, height);
            gc.dispose();
            return image;
        } else {
            return null;
        }
    }
    
    /***
     * 
     * @param gc
     * @param display
     * @param image
     *            A image object
     * @param verticeList
     *            Coordinate point set
     * @param verticalPointNum
     *            Number of vertical points
     * @param horizontalPointNum
     *            Number of horizontal points
     */
    public static void cut(GC gc, Display display, Image image, List<Integer> verticeList, int verticalPointNum,
            int horizontalPointNum) {
        if (image == null || verticalPointNum <= 1) {
            return;
        }
        int imageWidth = image.getImageData().width;
        int imageHeight = image.getImageData().height;
        int i, j;
        List<Integer> vertices = new ArrayList<Integer>();
        List<Integer> indices = new ArrayList<Integer>();
        List<Float> uvtData = new ArrayList<Float>();

        for (i = 0; i < horizontalPointNum; i++) {
            for (j = 0; j < verticalPointNum; j++) {
                vertices.add(i * imageWidth / (horizontalPointNum - 1));
                vertices.add(j * imageHeight / (verticalPointNum - 1));
            }
        }
        for (j = 0; j < horizontalPointNum; j++) {
            for (i = 0; i < verticalPointNum; i++) {

                int temp = (i + j * verticalPointNum);
                if (i != (verticalPointNum - 1)
                        && (temp + verticalPointNum + 1) < verticalPointNum * horizontalPointNum) {
                    indices.add(temp);
                    indices.add(temp + verticalPointNum);
                    indices.add(temp + 1);

                    indices.add(temp + verticalPointNum);
                    indices.add(temp + 1);
                    indices.add(temp + verticalPointNum + 1);
                }
            }
        }
        for (i = 0; i < 2 * verticalPointNum * horizontalPointNum; i++) {
            if (i % 2 == 0) {
                uvtData.add((float) (vertices.get(i)
                        / (vertices.get(2 * verticalPointNum * horizontalPointNum - 2) - vertices.get(0) + 0.0)));
            } else {
                uvtData.add((float) (vertices.get(i)
                        / (vertices.get(2 * verticalPointNum * horizontalPointNum - 1) - vertices.get(1) + 0.0)));
            }
        }
        vertices = verticeList;
        Rectangle rect = gc.getClipping();
        twistImage(gc, display, image, vertices, indices, uvtData);
        Transform transform = new Transform(display);
        transform.setElements(1, 0, 0, 1, 0, 0);
        gc.setTransform(transform);
        gc.setClipping(rect);
    }
    
    /***
     * 
     * @param gc
     * @param display
     * @param image
     * @param vertice
     *            Composed of digital vector, where each pair of numbers is
     *            treated as a coordinate (x, y). Vertices are required.
     * @param indices
     *            A vector composed of integers or indexes, where every three
     *            indexes define a triangle.
     * @param uvtData
     *            By the standards used to apply texture mapping coordinates
     *            vector consisting of. Each coordinate refers to a point on the
     *            bitmap used to fill.
     */
    private static void twistImage(GC gc, Display display, Image image, List<Integer> vertice, List<Integer> indices,
            List<Float> uvtData) {
        int i, j, l = indices.size(), k;
        float sw, sh;
        int imageWidth = image.getImageData().width;
        int imageHeight = image.getImageData().height;
        for (i = 0, j = 0; i < l; i += 3) {
            // gc.setAdvanced(true);
            Transform transform = new Transform(display);
            transform.setElements(1, 0, 0, 1, 0, 0);
            gc.setTransform(transform);

            Path path = new Path(display);
            path.moveTo(vertice.get(indices.get(i) * 2), vertice.get(indices.get(i) * 2 + 1));
            path.lineTo(vertice.get(indices.get(i + 1) * 2), vertice.get(indices.get(i + 1) * 2 + 1));
            path.lineTo(vertice.get(indices.get(i + 2) * 2), vertice.get(indices.get(i + 2) * 2 + 1));
            path.lineTo(vertice.get(indices.get(i) * 2), vertice.get(indices.get(i) * 2 + 1));
            path.close();
            gc.setClipping(path);

            if (i % 6 == 0) {
                sw = -1;
                float w = (uvtData.get(indices.get(i + 1 + j) * 2) - uvtData.get(indices.get(i + j) * 2)) * imageWidth;
                float h = (uvtData.get(indices.get(i + 2) * 2 + 1) - uvtData.get(indices.get(i) * 2 + 1)) * imageHeight;
                if (j == 0 && w < 0) {
                    for (k = i + 9; k < l; k += 3) {
                        if (uvtData.get(indices.get(i + 2) * 2 + 1) == uvtData.get(indices.get(k + 2) * 2 + 1)) {
                            j = k - i;
                            break;
                        }
                    }
                    if (j == 0) {
                        j = l - i;
                    }
                    w = (uvtData.get(indices.get(i + 1 + j) * 2) - uvtData.get(indices.get(i + j) * 2)) * imageWidth;
                }
                if (i + j >= l) {
                    w = (uvtData.get(indices.get(i + j - l) * 2) - uvtData.get(indices.get(i + 1) * 2)) * imageWidth;
                    sw = uvtData.get(indices.get(i) * 2) == 1 ? 0 : imageWidth * uvtData.get(indices.get(i) * 2) + w;
                    if (sw > imageWidth) {
                        sw -= imageWidth;
                    }
                } else {
                    sw = imageWidth * uvtData.get(indices.get(i + j) * 2);
                }
                sh = imageHeight * uvtData.get(indices.get(i) * 2 + 1);
                if (h < 0) {
                    h = (uvtData.get(indices.get(i + 2 - (i > 0 ? 6 : -6)) * 2 + 1)
                            - uvtData.get(indices.get(i - (i > 0 ? 6 : -6)) * 2 + 1)) * imageHeight;
                    sh = 0;
                }
                float t1 = (vertice.get(indices.get(i + 1) * 2) - vertice.get(indices.get(i) * 2)) / w;
                float t2 = (vertice.get(indices.get(i + 1) * 2 + 1) - vertice.get(indices.get(i) * 2 + 1)) / w;
                float t3 = (vertice.get(indices.get(i + 2) * 2) - vertice.get(indices.get(i) * 2)) / h;
                float t4 = (vertice.get(indices.get(i + 2) * 2 + 1) - vertice.get(indices.get(i) * 2 + 1)) / h;

                transform.setElements(t1, t2, t3, t4, vertice.get(indices.get(i) * 2),
                        vertice.get(indices.get(i) * 2 + 1));
                gc.setTransform(transform);
                gc.drawImage(image,
                        new BigDecimal((image.getImageData().x + sw)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal((image.getImageData().y + sh)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal(w).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal(h).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(), 0, 0,
                        new BigDecimal(w).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal(h).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
            } else {
                float w = (uvtData.get(indices.get(i + 2 + j) * 2) - uvtData.get(indices.get(i + 1 + j) * 2))
                        * imageWidth;
                float h = (uvtData.get(indices.get(i + 2) * 2 + 1) - uvtData.get(indices.get(i) * 2 + 1)) * imageHeight;
                if (j == 0 && w < 0) {
                    for (k = i + 9; k < l; k += 3) {
                        if (uvtData.get(indices.get(i + 2) * 2 + 1) == uvtData.get(indices.get(k + 2) * 2 + 1)) {
                            j = k - i;
                            break;
                        }
                    }
                    if (j == 0) {
                        j = l - i;
                    }
                    w = (uvtData.get(indices.get(i + 2 + j) * 2) - uvtData.get(indices.get(i + 1 + j) * 2))
                            * imageWidth;
                }
                if (i + 1 + j >= l) {
                    w = (uvtData.get(indices.get(i + 1 + j - l) * 2) - uvtData.get(indices.get(i + 2) * 2))
                            * imageWidth;
                    sw = (uvtData.get(indices.get(i + 1) * 2) == 1 ? 0
                            : imageWidth * uvtData.get(indices.get(i + 1) * 2) + w);
                    if (sw > imageWidth) {
                        sw -= imageWidth;
                    }
                } else {
                    sw = imageWidth * uvtData.get(indices.get(i + 1 + j) * 2);
                }
                sh = imageHeight * uvtData.get(indices.get(i) * 2 + 1);
                if (h < 0) {
                    h = (uvtData.get(indices.get(i + 2 - (i > 0 ? 6 : -6)) * 2 + 1)
                            - uvtData.get(indices.get(i - (i > 0 ? 6 : -6)) * 2 + 1)) * imageHeight;
                    sh = 0;
                }
                float t1 = (vertice.get(indices.get(i + 2) * 2) - vertice.get(indices.get(i + 1) * 2)) / w;
                float t2 = (vertice.get(indices.get(i + 2) * 2 + 1) - vertice.get(indices.get(i + 1) * 2 + 1)) / w;
                float t3 = (vertice.get(indices.get(i + 2) * 2) - vertice.get(indices.get(i) * 2)) / h;
                float t4 = (vertice.get(indices.get(i + 2) * 2 + 1) - vertice.get(indices.get(i) * 2 + 1)) / h;
                transform.setElements(t1, t2, t3, t4, vertice.get(indices.get(i + 1) * 2),
                        vertice.get(indices.get(i + 1) * 2 + 1));
                gc.setTransform(transform);
                gc.drawImage(image,
                        new BigDecimal((image.getImageData().x + sw)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal((image.getImageData().y + sh)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal(w).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal(h).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(), 0,
                        new BigDecimal(-h).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal(w).setScale(0, BigDecimal.ROUND_HALF_UP).intValue(),
                        new BigDecimal(h).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
            }
            transform.dispose();
        }
    }
