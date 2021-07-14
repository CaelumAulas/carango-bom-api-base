package br.com.caelum.carangobom.infra.controller;

import br.com.caelum.carangobom.CarangoBomApiApplication;
import br.com.caelum.carangobom.domain.entity.Vehicle;
import br.com.caelum.carangobom.infra.controller.request.CreateVehicleRequest;
import br.com.caelum.carangobom.infra.jpa.entity.MarcaJpa;
import br.com.caelum.carangobom.infra.jpa.entity.VehicleJpa;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { CarangoBomApiApplication.class })
@WebAppConfiguration
@Transactional
class VehicleControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private MockMvc mockMvc;


    private MarcaJpa createMarca(MarcaJpa marcaJpa){
        entityManager.persist(marcaJpa);
        return marcaJpa;
    }

    private VehicleJpa createVehicle(VehicleJpa vehicleJpa){
        entityManager.persist(vehicleJpa);
        return vehicleJpa;
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldCreateAnVehicle() throws Exception {
        MarcaJpa marcaJpa = this.createMarca(new MarcaJpa("Audi"));
        String model = "Audi";
        double price = 200000;
        int year = 2010;
        Long marcaId = marcaJpa.getId();
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(model,price,year,marcaId);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/vehicle")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(model))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(price))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(year))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca.id").value(marcaId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca.nome").value(marcaJpa.getNome()))
                .andReturn();
    }

    @Test
    void shouldReturn404BecauseTheMarcaDoesntExists() throws Exception {
        String model = "Audi";
        double price = 200000;
        int year = 2010;
        Long marcaId =100L;
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(model,price,year,marcaId);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/vehicle")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void shouldReturnTheErrorsWhenTheRequestIsInvalidOnCreateVehicle() throws Exception{
        String model = "";
        double price = 0;
        int year = -30;
        Long marcaId = -20L;
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(model,price,year,marcaId);
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/vehicle")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    void souldUpdateTheVehicle() throws Exception {
        MarcaJpa marcaJpa = this.createMarca(new MarcaJpa("Ford"));
        MarcaJpa newMarcaJpa = this.createMarca(new MarcaJpa("Audi"));
        VehicleJpa vehicleJpa = createVehicle(new VehicleJpa(null,"Ford k",2002,15000.0,marcaJpa));

        String model = "Audi";
        double price = 200000;
        int year = 2010;
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(model,price,year, newMarcaJpa.getId());

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/vehicle/{id}",vehicleJpa.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vehicleJpa.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(model))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(price))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(year))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca.id").value(newMarcaJpa.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca.nome").value(newMarcaJpa.getNome()))
                .andReturn();
    }

    @Test
    void shouldReturn404OnUpdateVehicleWhenVehicleDoesntExist() throws Exception{
        MarcaJpa marcaJpa = this.createMarca(new MarcaJpa("Ford"));
        String model = "Audi";
        double price = 200000;
        int year = 2010;
        Long vehicleId = 1000L;
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(model,price,year, marcaJpa.getId());

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/vehicle/{id}",vehicleId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturn404OnUpdateVehicleWhenMarcaDoesntExist() throws Exception{
        MarcaJpa marcaJpa = this.createMarca(new MarcaJpa("Ford"));
        VehicleJpa vehicleJpa = createVehicle(new VehicleJpa(null,"Ford k",2002,15000.0,marcaJpa));
        String model = "Audi";
        double price = 200000;
        int year = 2010;
        Long fakeMarca = 11L;
        CreateVehicleRequest createVehicleRequest = new CreateVehicleRequest(model,price,year,fakeMarca);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/vehicle/{id}",vehicleJpa.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createVehicleRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}