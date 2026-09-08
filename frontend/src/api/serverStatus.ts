import { client } from "./client";

// Mirrors ch.acend.trackit.domain.ServerStatus field for field.
export type WeatherCondition = "SUNNY" | "CLOUDY" | "RAINY" | "SNOWY";

export interface Weather {
  temperatureCelsius: number;
  condition: WeatherCondition;
}

export interface ServerStatus {
  dateTime: string;
  zoneId: string;
  weather: Weather;
}

export async function getServerStatus(): Promise<ServerStatus> {
  const response = await client.get<ServerStatus>("/server-status");
  return response.data;
}
