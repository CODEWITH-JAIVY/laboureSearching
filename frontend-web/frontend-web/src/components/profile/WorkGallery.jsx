export default function WorkGallery({ images = [], onUpload, onDelete }) {
  return (
    <div className="mt-10">
      <div className="flex justify-between mb-4">
        <h3 className="text-xl font-bold">My Work</h3>

        <label className="bg-blue-600 text-white px-4 py-2 rounded-lg cursor-pointer">
          Upload
          <input
            type="file"
            hidden
            onChange={(e) => onUpload(e.target.files[0])}
          />
        </label>
      </div>

      {images.length === 0 ? (
        <p className="text-gray-500 text-sm">No work images uploaded</p>
      ) : (
        <div className="grid grid-cols-3 gap-3">
          {images.map((img) => (
            <div key={img.id} className="relative group">
              <img
                src={img.imageUrl}
                className="w-full h-32 object-cover rounded-lg"
              />

              <button
                onClick={() => onDelete(img.id)}
                className="absolute top-2 right-2 bg-red-500 text-white text-xs px-2 py-1 rounded opacity-0 group-hover:opacity-100"
              >
                ✕
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
