package subway.service;

import subway.domain.Line;
import subway.domain.repository.LineRepository;
import subway.domain.Station;
import subway.domain.repository.StationRepository;

import java.util.List;

public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public void addLine(String name, String[] stationNames) {
        Line line = new Line(name);
        for (String stationName : stationNames) {
            Station station = stationRepository.stations().stream()
                    .filter(s -> s.getName().equals(stationName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 역입니다."));
            line.addStation(station);
        }
        lineRepository.addLine(line);
    }

    public void addLine(String name, String upStationName, String downStationName) {
        Station upStation = stationRepository.stations().stream()
                .filter(s -> s.getName().equals(upStationName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상행 종점역입니다."));
        Station downStation = stationRepository.stations().stream()
                .filter(s -> s.getName().equals(downStationName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 하행 종점역입니다."));
        Line line = new Line(name, upStation, downStation);
        lineRepository.addLine(line);
    }

    public void deleteLine(String name) {
        if (!lineRepository.deleteLineByName(name)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 노선입니다.");
        }
    }

    public List<Line> getLines() {
        return lineRepository.lines();
    }
}
