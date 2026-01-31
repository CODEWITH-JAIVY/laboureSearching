import { useEffect, useState, useContext } from "react";
import { useNavigate } from "react-router-dom";

import { getLabourTypesApi, getGenderTypesApi } from "../../api/meta.api";
import axios from "../../utils/axios";
import { AuthContext } from "../../context/AuthContext";

export default function CreateLabourProfile() {
  const navigate = useNavigate();
  const { refreshUser } = useContext(AuthContext);

  const [labourTypes, setLabourTypes] = useState([]);
  const [genderTypes, setGenderTypes] = useState([]);

  const [form, setForm] = useState({
    labourType: "",
    genderType: "ANY",
    employmentType: "",
    workingHours: "",
    about: "",
    availableToday: false,
    latitude: "",
    longitude: "",
  });

  const [educationList, setEducationList] = useState([]);
  const [profileImage, setProfileImage] = useState(null);
  const [workPhotos, setWorkPhotos] = useState([]);

  /* ================= LOAD ENUMS (BEST PRACTICE) ================= */
  useEffect(() => {
    getLabourTypesApi().then(setLabourTypes).catch(console.error);
    getGenderTypesApi().then(setGenderTypes).catch(console.error);
  }, []);

  /* ================= HANDLERS ================= */
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({ ...form, [name]: type === "checkbox" ? checked : value });
  };

  /* ================= EDUCATION ================= */
  const addEducation = () =>
    setEducationList([
      ...educationList,
      { qualification: "", institute: "", passingYear: "" },
    ]);

  const updateEducation = (i, field, value) => {
    const copy = [...educationList];
    copy[i][field] = value;
    setEducationList(copy);
  };

  const removeEducation = (i) =>
    setEducationList(educationList.filter((_, idx) => idx !== i));

  /* ================= LIVE LOCATION ================= */
  const pickLiveLocation = () => {
    navigator.geolocation.getCurrentPosition((pos) => {
      setForm({
        ...form,
        latitude: pos.coords.latitude,
        longitude: pos.coords.longitude,
      });
    });
  };

  /* ================= SUBMIT ================= */
  const submit = async () => {
    const payload = {
      labourType: form.labourType,
      genderType: form.genderType,
      employmentType: form.employmentType,
      workingHours: form.workingHours,
      about: form.about,
      availableToday: form.availableToday,
      liveLocation: {
        latitude: form.latitude,
        longitude: form.longitude,
      },
      educationList,
    };

    const formData = new FormData();
    formData.append(
      "profile",
      new Blob([JSON.stringify(payload)], { type: "application/json" }),
    );

    if (profileImage) formData.append("profileImage", profileImage);
    workPhotos.forEach((f) => formData.append("workPhotos", f));

    await axios.post("/labour/profile", formData);
    await refreshUser();
    navigate("/labour/dashboard");
  };

  /* ================= UI ================= */
  return (
    <div className="min-h-screen bg-gray-100 py-10">
      <div className="max-w-3xl mx-auto bg-white rounded-xl shadow p-8 space-y-8">
        <h1 className="text-2xl font-bold text-center text-gray-800">
          Create Labour Profile
        </h1>

        {/* ================= WORK DETAILS ================= */}
        <section className="space-y-4">
          <h2 className="section-title">Work Details</h2>

          {/* Labour Type */}
          <select
            name="labourType"
            value={form.labourType}
            onChange={handleChange}
            className="input"
          >
            <option value="">Select Work Type</option>
            {labourTypes.map((t) => (
              <option key={t} value={t}>
                {t.replaceAll("_", " ")}
              </option>
            ))}
          </select>

          {/* Gender */}
          <select
            name="genderType"
            value={form.genderType}
            onChange={handleChange}
            className="input"
          >
            {genderTypes.map((g) => (
              <option key={g} value={g}>
                {g}
              </option>
            ))}
          </select>

          <select
            name="employmentType"
            value={form.employmentType}
            onChange={handleChange}
            className="input"
          >
            <option value="">Employment Type</option>
            <option value="FULL_TIME">Full Time</option>
            <option value="PART_TIME">Part Time</option>
          </select>

          <input
            name="workingHours"
            value={form.workingHours}
            onChange={handleChange}
            placeholder="Working Hours (9 AM - 6 PM)"
            className="input"
          />

          <textarea
            name="about"
            value={form.about}
            onChange={handleChange}
            placeholder="Describe your work experience"
            className="input h-28 resize-none"
          />

          <label className="flex items-center gap-2 text-sm">
            <input
              type="checkbox"
              name="availableToday"
              onChange={handleChange}
            />
            Available today
          </label>
        </section>

        {/* ================= EDUCATION ================= */}
        <section className="space-y-3">
          <h2 className="section-title">Education / Qualification</h2>

          {educationList.map((edu, i) => (
            <div key={i} className="grid grid-cols-3 gap-3">
              <input
                className="input"
                placeholder="Qualification"
                onChange={(e) =>
                  updateEducation(i, "qualification", e.target.value)
                }
              />
              <input
                className="input"
                placeholder="Institute"
                onChange={(e) =>
                  updateEducation(i, "institute", e.target.value)
                }
              />
              <input
                className="input"
                placeholder="Year"
                onChange={(e) =>
                  updateEducation(i, "passingYear", e.target.value)
                }
              />
              <button
                type="button"
                onClick={() => removeEducation(i)}
                className="text-red-500 text-sm col-span-3 text-right"
              >
                Remove
              </button>
            </div>
          ))}

          <button
            type="button"
            onClick={addEducation}
            className="text-blue-600 text-sm"
          >
            ➕ Add Education
          </button>
        </section>

        {/* ================= LOCATION ================= */}
        <section className="space-y-3">
          <h2 className="section-title">Live Location</h2>

          <button
            onClick={pickLiveLocation}
            className="w-full py-2 rounded bg-gray-200 hover:bg-gray-300 text-sm"
          >
            📍 Use My Current Location
          </button>

          {form.latitude && (
            <p className="text-xs text-gray-600">
              Lat: {form.latitude}, Lng: {form.longitude}
            </p>
          )}
        </section>

        {/* ================= IMAGES ================= */}
        <section className="space-y-3">
          <h2 className="section-title">Photos</h2>

          <div>
            <label className="block text-sm mb-1">Profile Image</label>
            <input
              type="file"
              onChange={(e) => setProfileImage(e.target.files[0])}
            />
          </div>

          <div>
            <label className="block text-sm mb-1">Work Photos</label>
            <input
              type="file"
              multiple
              onChange={(e) => setWorkPhotos(Array.from(e.target.files))}
            />
          </div>
        </section>

        {/* ================= SUBMIT ================= */}
        <button
          onClick={submit}
          className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-lg font-semibold"
        >
          Save & Continue
        </button>
      </div>
    </div>
  );
}
