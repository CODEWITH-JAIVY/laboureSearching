export default function ProfileHeader({
  user,
  uploading,
  uploadPercent,
  onEdit,
  onChangePhoto,
}) {
  return (
    <div className="bg-[#FDFEFE] rounded-2xl border p-6 flex gap-6 items-center">
      {/* IMAGE */}
      <div className="relative">
        <div className="p-1 rounded-full bg-gradient-to-tr from-blue-500 to-orange-400">
          <img
            src={
              user.profileImageUrl ||
              `https://ui-avatars.com/api/?name=${user.name}`
            }
            className="w-28 h-28 rounded-full object-cover bg-white"
          />
        </div>

        {uploading && (
          <div className="absolute inset-0 bg-black/60 rounded-full flex items-center justify-center text-white text-sm">
            {uploadPercent}%
          </div>
        )}
      </div>

      {/* INFO */}
      <div className="flex-1">
        <h2 className="text-2xl font-semibold">{user.name}</h2>
        <p className="text-gray-500 text-sm">{user.email}</p>

        <div className="grid grid-cols-2 md:grid-cols-3 gap-3 mt-4">
          <Info label="Mobile" value={user.mobile} />
          <Info label="Type" value={user.userType} />
          <Info
            label="Rating"
            value={user.rating ? `⭐ ${user.rating}` : "-"}
          />
          <Info label="Wage" value={user.wage ? `₹ ${user.wage}/day` : "-"} />
          <Info
            label="Status"
            value={user.availableToday ? "Active Today" : "Inactive"}
            green={user.availableToday}
          />
        </div>

        {/* ACTIONS */}
        <div className="flex gap-4 mt-4">
          <label className="text-blue-600 text-sm cursor-pointer hover:underline">
            Change photo
            <input
              type="file"
              hidden
              accept="image/*"
              onChange={onChangePhoto}
            />
          </label>

          <button
            onClick={onEdit}
            className="text-sm text-gray-600 hover:text-gray-800"
          >
            Edit profile
          </button>
        </div>
      </div>
    </div>
  );
}

function Info({ label, value, green }) {
  return (
    <div>
      <p className="text-xs text-gray-400 uppercase">{label}</p>
      <p className={`font-semibold ${green ? "text-green-600" : ""}`}>
        {value || "-"}
      </p>
    </div>
  );
}
