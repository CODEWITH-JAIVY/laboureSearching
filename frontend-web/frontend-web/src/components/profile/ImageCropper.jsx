import Cropper from "react-easy-crop";
import { useState } from "react";
import { getCroppedImg } from "../../utils/cropImage";

export default function ImageCropper({ image, onComplete, onClose }) {
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);
  const [pixels, setPixels] = useState(null);

  return (
    <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">
      <div className="bg-white p-4 rounded-lg w-[350px] h-[400px] relative">
        <Cropper
          image={image}
          crop={crop}
          zoom={zoom}
          aspect={1}
          onCropChange={setCrop}
          onZoomChange={setZoom}
          onCropComplete={(_, croppedPixels) => setPixels(croppedPixels)}
        />

        <div className="absolute bottom-4 left-0 right-0 flex justify-center gap-4">
          <button onClick={onClose}>Cancel</button>
          <button
            className="bg-blue-600 text-white px-4 py-1 rounded"
            onClick={async () => {
              const croppedFile = await getCroppedImg(image, pixels);
              onComplete(croppedFile);
            }}
          >
            Crop & Upload
          </button>
        </div>
      </div>
    </div>
  );
}
