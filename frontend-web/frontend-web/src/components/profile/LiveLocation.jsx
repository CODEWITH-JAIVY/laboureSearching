import { useEffect, useState } from "react";
import { saveLiveLocationService } from "../../services/profile.service";

export default function LiveLocation({ onSaved }) {
  const [coords, setCoords] = useState(null);
  const [manual, setManual] = useState(false);

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (pos) =>
        setCoords({
          lat: pos.coords.latitude,
          lng: pos.coords.longitude,
        }),
      () => setManual(true)
    );
  }, []);

  const save = async () => {
    await saveLiveLocationService(coords);
    onSaved?.();
  };

  return (
    <div className="bg-[#F6F8FA] p-6 rounded-xl space-y-4">
      {!manual && coords && (
        <>
          <p>Lat: {coords.lat}</p>
          <p>Lng: {coords.lng}</p>
          <button onClick={save} className="btn">Save</button>
          <button onClick={() => setManual(true)}>Change manually</button>
        </>
      )}

      {manual && (
        <>
          <input className="input" placeholder="Latitude" />
          <input className="input" placeholder="Longitude" />
          <button onClick={save} className="btn">Save</button>
        </>
      )}
    </div>
  );
}
